#!/bin/bash
#readlink -f "$0"
path1=$PWD
#echo $path1
cd $path1
#echo $PWD
echo "Проверка наличия Java в системе"
java1 -version
if [ $? -eq 0 ]
then
 
  echo "Java установлена"
else
 
  echo "Java не установлена. Установить? yes/no"
  read $otvet
  echo $otvet
  y="yes"
  if [ "$otvet" == "$y" ]; then
    echo $?
    echo "Устанавливаем Java"
  else
    echo $?
    echo "Инсталяция без Java невозможна"
    #exit
  fi	
fi
echo "конец"
