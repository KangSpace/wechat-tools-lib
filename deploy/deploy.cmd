mvn clean deploy -P release -Dmaven.test.skip=true -Dgpg.passphrase=

-- 1. add the gpg key
pub   rsa3072 2020-12-08 [SC] [expires: 2022-12-08]
      E210C3841D92F620DE9B558F3938BAF259F47187
uid                      oss.sonatype.org
sub   rsa3072 2020-12-08 [E] [expires: 2022-12-08]
-- 2. configure maven.setting <server> tag for snapshotRepository and repository(distributionManagement) in pom file.
<server>
      <id>oss.sonatype.org</id>
      <username></username>
      <password></password>
</server>