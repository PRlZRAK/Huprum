<?php
class db extends PDO
{
	var $host 	= "localhost";
	var $user 	= "user";
	var $pass 	= "wO+7Aq5cOht";
	var $db 	= 'huprum';
	var $charset = 'utf8';
	function __construct()
	{ 	
		$dsn = "mysql:host=$this->host;dbname=$this->db;charset=$this->charset";
		$opt = array(
    	PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
    	PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
		PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8'
		);
		//parent:: = new PDO($dsn, $this->user, $this->pass, $opt);
		parent::__construct($dsn, $this->user, $this->pass, $opt);
	}
	static function prePars($values)
	{
		$tmp = '';
		foreach ($values as $key => $value)
		{
			$tmp.= $key.'=\''.$value.'\', ';
		}
		return substr($tmp, 0, -2);
	}
}