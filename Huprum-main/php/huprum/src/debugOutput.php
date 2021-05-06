<?php
class debugOutput
{
	private  $file;
	private static $tabul;
	function debugOutput($url = '', $mode='w')
	{ 
		if (!isset($url) || $url == '') 
			$url = 'debug.txt';
		if (!isset($mode) || $mode == '') 
			$mode = 'w';
		$this->file = fopen ( $url, $mode );
		if (! $this->file)
		{
			echo ("Ошибка открытия файла отладки");
		}
		$this->putStr(date("F j, Y, g:i a"));
	}
	private function putStr($str)
	{
		fputs ( $this->file, $str."\n" );
	}
	public function put($var, $name='', $deep = 99)
	{
		$type = gettype (  $var );
		switch ($type)
		{				 
			case "array":
				
				$this->putStr($this->tabul."[$name] = (array): {"); 
				if (strlen($this->tabul) > $deep) break; 
				foreach ($var as $key => $val)
				{
					$this->tabul.= "\t";
					$this->put($val, $key);
					$this->tabul = substr($this->tabul, 0, strlen($this->tabul)-1);
					
				}
				$this->putStr($this->tabul."} [/$name]");
				break;
			case "NULL":
				$this->putStr($this->tabul."[".$name."] = ($type)");
				break;
			case "object":
				$this->putStr($this->tabul."[$name] = (object): {"); 
				if (strlen($this->tabul) > $deep) break; 
				if (count($var) > 0) 
				{
					$ft = true;
					foreach ($var as $key => $val)
					{
						if ($ft)
						{
							fputs ( $this->file, $this->tabul."\tproperties:\n" );
							$ft = !$ft;
						}
						$this->tabul.= "\t";
						$this->put($val, $key);
						$this->tabul = substr($this->tabul, 0, strlen($this->tabul)-1);
					}
				}
				$methods = get_class_methods ( $var );  
				if (count( $methods > 0 )) 
				{ 
					$ft = true;
					foreach ($methods as $key => $val)
					{
						if ($ft)
						{
							fputs ( $this->file, $this->tabul."\tmethods:\n" );
							$ft = !$ft;
						}
						fputs ( $this->file, $this->tabul."\t->$val\n" );
					}
				}
				$this->putStr($this->tabul."} [/$name]");
				break;
			case "string":
				$this->putStr($this->tabul."[".$name."] = ($type)\"".$var."\""); 
				break;
			default:
				$this->putStr($this->tabul."[".$name."] = ($type)".$var); 
		}
	}
	public function close()
	{ 
		fclose($this->file);
	}
}