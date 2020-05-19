#! /bin/bash

#A Script to Collect Server Usage Data

#Assign input arguments to variables
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_pass=$5

#Error handling
if [ "$1" == "" ]; then
   echo "Please Specify the Hostname!" 1>&2
   exit 1
fi

if [ "$2" == "" ]; then
   echo "Please Specify the Port Address!" 1>&2
   exit 1
fi

if [ "$3" == "" ]; then
   echo "Please Specify the Name of the Database!" 1>&2
   exit 1
fi

if [ "$4" == "" ]; then
   echo "Please Input a Username!" 1>&2
   exit 1
fi 

if [ "$5" == "" ]; then
   echo "Please Input a Password!" 1>&2
   exit 1
fi

#Assign usage info to variables
use_data=$(cat /proc/meminfo)
stat=$(vmstat)
memory_free=$(echo "$use_data"  | egrep "^MemFree:" | awk '{print $2}' | xargs)
cpu_idle=$(echo "$stat" | egrep -A1 "id"| egrep -v "id"| awk '{print $15}' | xargs)
cpu_kernel=$(echo "$stat" | egrep -A1 "id"| egrep -v "sy"| awk '{print $14}' | xargs)
disk_io=$(echo "$(vmstat -d)" | egrep -A1 "cur"| egrep -v "cur"| awk '{print $10}' | xargs)
disk_available=$(echo "$(df -BM /)" | egrep -A1 "Available"| egrep -v "Available"| awk '{print $4}' | sed 's/M//' | xargs)
timestamp=$(date -u "+%F %T")

#Define insert statement
insert_stmt="INSERT INTO host_usage (timestamp,host_id,memory_free,cpu_idle,cpu_kernel,disk_io,disk_available) 
VALUES 
 (
   '$timestamp',
   (SELECT LAST_VALUE FROM host_info_id_seq),
   '$memory_free',
   '$cpu_idle',
   '$cpu_kernel',
   '$disk_io',
   '$disk_available'
)"

psql -h $psql_host -p $psql_port -U $psql_user -d $db_name -c "$insert_stmt"

exit 0
