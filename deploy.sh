#!/bin/bash
echo $1
echo $2
# Password does not matter
sshpass -p 'alwhndjkawhdkhjawd' scp $1 root@$2:/mnt/us/documents/
