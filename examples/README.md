ndwritten bug-triggering tests
This sub-module includes bug-triggering tests written by hand.

### Patch
Each issue typically has 3 patch files (*.patch):
1. The author's original patch (<issue_id>.patch). The patch is derived by executing `git diff <commit hash>^1 <commit hash>`
2. Patch to reproduce buggy code (<issue_id>.before.patch)
3. Patch to reproduce solved code (<issue_id>.after.patch)

If the reproduction trial is not successful, also please make the current effort into a patch and name it <issue_id>.incomplete.patch. And developer should document the reasons and problems why the reproduction is unsuccessful

Sometimes the .before.patch and .after.patch can't be separated (e.g., the author's patch doesn't change previous code but add a new, alternative implementation of a class). This way, I suggest one can name the big patch as .before.after.patch (please refer to hdfs-12998)

### Script
And a script file (reproduce.sh)
The script should contain information including
1. Issue git commit hash (usually by executing `git log -r --grep <ISSUE_ID> --all`). Note that there might be multiple commits about one Jira issue because the the problem and the patch may apply to multiple branches or tags. The developer should decide which commit to use (typically the one that applies to the major version/release that is supposed to be more stable)
2. Reproduction cluster (e.g., Chameleon Cloud)
3. Physical machine type (specific to the cloud cluster)
4. Linux Distro and version (e.g., Ubuntu-22.04)
5. Java version (e.g., Java 8 or Java 11)
6. Installed packages (either self built or downloaded via package management tools like apt or yum)
7. Any other changes to make (e.g., change systemd settings, change limits.conf)
8. Specific commands to build, clean the system, commands to apply the patch, commands to run the tests.
9. Specific commands to get the results of the experiments (e.g., which log files have the results, and what pattern string of grep to use to filter the results logs). This one was missing on bugs that were reproduced by Frank Li. But please add them in future reproduction.
