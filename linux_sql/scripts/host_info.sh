#! /bin/bash

#A Script to Collect User Machine Information

#Assign input arguments to variables
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_pass=$5

#Error Handling
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

#Assign hardware spec into variables
lscpu_out=$(lscpu)
hostname=$(hostname -f)
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out"  | egrep "^Architecture" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out"  | egrep "^Model name:" | awk '{print $3,$4,$5,$6,$7}' | xargs)
cpu_mhz=$(echo "$lscpu_out"  | egrep "^CPU MHz:" | awk '{print $3}' | xargs)
l2_cache=$(echo "$lscpu_out"  | egrep "^L2 cache:" | awk '{print $3}'| sed 's/K//' | xargs)
total_mem=$(echo "$lscpu_out"  | egrep "^L3 cache:" | awk '{print $3}' | sed 's/K//' | xargs)
timestamp=$(date -u "+%F %T")

#Define insert statement
insert_stmt="INSERT INTO host_info (hostname,cpu_number,cpu_architecture,cpu_model,cpu_mhz,L2_cache,total_mem,timestamp) 
VALUES 
 (
   '$hostname',
   '$cpu_number',
   '$cpu_architecture',
   '$cpu_model',
   '$cpu_mhz',
   '$l2_cache',
   '$total_mem',
   '$timestamp'
 )"

psql -h $psql_host -p $psql_port -U $psql_user -d $db_name -c "$insert_stmt"

exit 0
