<?php

$host		= "localhost"; // Use Local Host Only      
$username	= "epiz_21606277_Customer";
$password	= ""; 
$db_name	= "epiz_21606277_Customer"; 
 

$link = mysqli_connect($host, $username, $password, "$db_name");

 
 
 $S_name= $_POST['Ename'];
 $S_mob= $_POST['Emob'];
$S_remark= $_POST['Eremark'];

 $Sql_Query = "INSERT INTO Customer(cust_name,cust_mobile,remark) VALUES ('osama',888,'remark')";

 if(mysqli_query($link,$Sql_Query))
{
 echo 'Registered Successfully';
}
else
{
 echo 'Something went wrong';
 }
 
mysqli_close($link);

?>





