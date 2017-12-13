import os

message = raw_input("\n" + "Enter Commit Message: ")
commit_command = "git commit -m " + "\"" + str(message) + "\""

os.system("git status")
os.system("git add .")
os.system(commit_command)
os.system("git push -u origin master")

print("Script Completed\n")
