#ARG REGISTERY=docker.io
#FROM $REGISTER/fabric8/java-alpine-openjdk11-jre:1.8
FROM fabric8/java-alpine-openjdk11-jre:1.8
#FROM openjdk:8-jre-alpine

WORKDIR /usr/share/tag

# Add the project jar & copy dependencies
ADD  target/csr-automation-*.*.?.jar csr-automation.jar
ADD  target/csr-automation-*.*.?-tests.jar csr-automation-tests.jar
ADD  target/libs libs

# Add the suite xmls
ADD *.xml /usr/share/tag/

# Add All Properties Files
ADD src/main/resources/*.properties src/main/resources/

# Add All Test Data Files
ADD src/main/resources/testdata/*.xlsx src/main/resources/testdata/
ADD src/main/resources/filestoupload/*.pdf src/main/resources/filestoupload/

# Command line to execute the test
ENTRYPOINT ["/usr/bin/java", "-cp", "csr-automation.jar:csr-automation-tests.jar:libs/*", "org.testng.TestNG", "./testng.xml"]
