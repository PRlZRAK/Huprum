#!/bin/bash
echo "Проверка наличия Java в системе"
java -version
if [ $? -eq 0 ]
then
  echo "Java установлена"
else
  echo "Java не установлена"
fi
