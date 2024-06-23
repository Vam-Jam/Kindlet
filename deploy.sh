#!/bin/bash
echo $1
echo $2
# If you have set up usbnetwork and not touched the settings, you can put whatever gibberish you want
# The kindle will accept any connection, but it still wants a password
sshpass -p 'alwhndjkawhdkhjawd' scp $1 root@$2:/mnt/us/documents/
