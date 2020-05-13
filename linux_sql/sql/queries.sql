--Querying Script to Group Hosts

--Group hosts by CPU number and sort by their memory size in descending order
SELECT 
   ROW_NUMBER() OVER (
      PARTITION BY cpu_number
      ORDER BY 
         total_mem DESC),
   id,
   total_mem
FROM 
   host_info; 

--Average used memory in percentage over 5 mins interval for each host

--Function for ronding to nearest 5 minutes (Credit to https://wiki.postgresql.org/wiki/Round_time)
CREATE FUNCTION round_time(TIMESTAMP WITH TIME ZONE) 
RETURNS TIMESTAMP WITH TIME ZONE AS $$ 
  SELECT date_trunc('hour', $1) + INTERVAL '5 min' * ROUND(date_part('minute', $1) / 5.0) 
$$ LANGUAGE SQL;

--Averaging over 5 min interval
SELECT 
   id,
   hostname,
   round_time(host_usage.timestamp),
   ROUND(AVG(((1.0*total_mem - memory_free)/total_mem)*100),1) AS Percentage
FROM
   host_info,
   host_usage
GROUP BY
   id,
   hostname,
   round_time(host_usage.timestamp);
  
