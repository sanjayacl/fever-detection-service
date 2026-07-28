# Jenkins Maven Installation Fix

## Error
```
java.io.IOException: Failed to install https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.16/apache-maven-3.9.16-bin.zip
to /var/lib/jenkins/tools/hudson.tasks.Maven_MavenInstallation/Maven
```

## Root Cause
Jenkins is configured to auto-download Maven 3.9.16 from Maven Central but is failing —
either the version is unavailable or the Jenkins server cannot reach the internet.

---

## Fix Options

### Option 1 — Install Maven manually on the Jenkins server (Recommended)

SSH into your Jenkins server and install Maven via the package manager:

```bash
# Ubuntu / Debian
sudo apt-get install -y maven

# Amazon Linux / RHEL / CentOS
sudo yum install -y maven
```

Then in the Jenkins UI:
1. Go to **Manage Jenkins → Tools → Maven installations**
2. Uncheck **"Install automatically"**
3. Set **MAVEN_HOME** to the installed path (e.g. `/usr/share/maven`)
4. Save

---

### Option 2 — Change the Maven version in Jenkins UI

If you want Jenkins to keep auto-installing Maven, switch to a known stable version:

1. Go to **Manage Jenkins → Tools → Maven installations**
2. Change the version from `3.9.16` to `3.9.9` (confirmed available on Maven Central)
3. Save and re-run the pipeline

---

### Option 3 — Use system Maven (skip the tools block)

If Maven is already installed on the Jenkins server, remove the `tools` block from the Jenkinsfile
so Jenkins uses whichever `mvn` is on the system `PATH`:

```groovy
// Remove or comment out this block in Jenkinsfile:
tools {
    maven 'Maven'
    jdk 'Java21'
}
```

The pipeline already calls `mvn` directly in `sh` steps, so this will work without any other changes.

---

## Verify Maven is installed on your Jenkins server

Run this from your local machine (replace with your Jenkins host):

```bash
ssh ec2-user@<jenkins-host> 'mvn -version'
```

If it returns a version, Option 3 is the quickest fix.

---

## Related: "release version 17 not supported" when running `mvn test` locally

## Error
```
error: release version 17 not supported
```

## Root Cause
`pom.xml` sets `<java.version>17</java.version>`, but the JDK on the machine running
Maven is older (e.g. JDK 11). This is a local/dev-machine issue, not a Jenkins issue —
it happens whenever `mvn` runs under a JDK older than 17.

## Fix
Install a JDK 17+ and point Maven at it:

```bash
# check what's currently active
java -version

# Ubuntu/Debian
sudo apt-get install -y openjdk-17-jdk

# Amazon Linux / RHEL
sudo yum install -y java-17-amazon-corretto-devel
```

Then set `JAVA_HOME` to the JDK 17 install before running `mvn`, e.g.:

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
mvn clean test
```

This matches what the Jenkinsfile already does (`tools { jdk 'Java21' }`), so Jenkins
builds are unaffected — this only matters when running Maven outside of Jenkins.

---

## Confirmed on this machine (2026-07-27)

Ran `java -version` and `mvn -version` here: only JDK 11.0.31 (Temurin) is installed,
and Maven 3.8.7 is running on top of it. Since `pom.xml` requires `java.version=17`,
`mvn spring-boot:run` / `mvn test` will fail on this machine with the "release version 17
not supported" error above until a JDK 17+ is installed and `JAVA_HOME` points to it.

If a JDK 17+ is already installed elsewhere on this machine, point `JAVA_HOME` at it and
re-run; otherwise install one (see the "Fix" section above) before running the app locally.
