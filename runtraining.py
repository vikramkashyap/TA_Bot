import paramiko
import os

def copy_directory(dir):
    if(os.path.isfile(dir)):
        ftp_client.put(dir,home_dir+'/'+dir)
        return
    files = os.listdir(dir)
    client.exec_command('mkdir '+dir)
    for file in files:
        copy_directory(dir+'/'+os.path.basename(file))

print('to run locally, just run the Main.java class')
print('for a remote server, robocode and java must be installed')
print('it also must be open for ssh')
print('enter the following information for the server')
home_dir = input('remote directory to copy to: ')
usr = input('username: ')
keyfile = input('key file path: ')
url = input('url: ')
key = paramiko.RSAKey.from_private_key_file(keyfile)
client = paramiko.SSHClient()
client.set_missing_host_key_policy(paramiko.AutoAddPolicy()) 
client.connect(hostname=url,username=usr,pkey=key)
copycode = input('is there a copy of the code on the server? (y/n)')
if(copycode=='n'):
    ftp_client = client.open_sftp()
    copy_directory('.')
    ftp_client.close()
client.exec_command('cd '+home_dir+'/bin')
client.exec_command('java -cp . ta_bot.Main')
