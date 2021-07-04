#!/bin/bash
echo "1. Определение версии Linux:"
if cat /etc/*release*  | grep 'Ubuntu 20.04'; then
  echo "Нет конфликта версий"
else
  echo "Версия Linux этого компьютера не совпадает"
  echo "с версией Linux отладки этого скрипта."
  echo "Может быть некоректным его выполнение."
  echo "Продолжить его выполнение? да/нет"
  read -p " " otvet
  echo "вы ввели:" $otvet
  if [ "$otvet" = "да" ]
  then
    echo "Переход к следующему шагу"
  else
    echo "Завершение работы"
    exit 1
  fi	
fi
echo "2. инсталяция Java:"
echo "Проверка наличия Java в системе"
java -version
if [ $? -eq 0 ]
then
  echo "Java в наличии"
else
  echo "Java отсутствует в этой системе."
  echo "Вы можете ее установить при помощи скрипта java_install.sh."
  echo "Этот скрипт запускается с правами администратора коммандой:"
  echo "sudo sh java_install.sh"
  echo "После этого повторите запуск этого скрипта"
  exit 1
fi
echo "3. Создание папки программы и копирование файлов:"
tukdir=$HOME/tuktuk
mkdir -p $tukdir
echo Папка программы - \"$tukdir\"
cp logo.png $tukdir
cp tuktuk.jar $tukdir
echo '#!/bin/bash' > $tukdir/tuktuk.sh
echo 'cd '$tukdir >> $tukdir/tuktuk.sh
echo 'java -jar tuktuk.jar' >> $tukdir/tuktuk.sh
chmod 777 $tukdir/tuktuk.sh
echo "4. Создание кнопки запуска на рабочем столе:"
desktop="$(xdg-user-dir DESKTOP)"
echo Папка рабочего стола - \"$desktop\"
echo '[Desktop Entry]' > $desktop/tuktuk.desktop
echo 'Version=1.0'  >> $desktop/tuktuk.desktop
echo 'Name=Messenger Tuktuk'  >> $desktop/tuktuk.desktop
echo 'GenericName=TukTuk'  >> $desktop/tuktuk.desktop
echo 'Comment=Communication tool for schoolchildren with TukTuk'  >> $desktop/tuktuk.desktop
echo 'Type=Application'  >> $desktop/tuktuk.desktop
echo 'Exec='sh $tukdir/tuktuk.sh  >> $desktop/tuktuk.desktop
echo 'Icon='$tukdir/logo.png  >> $desktop/tuktuk.desktop
echo 'Keywords=tuktuk;connect;'  >> $desktop/tuktuk.desktop
echo 'Terminal=false'  >> $desktop/tuktuk.desktop
echo 'Categories=Application;'  >> $desktop/tuktuk.desktop
chmod +x "$desktop/tuktuk.desktop"
echo "5. Подготовка кнопки запуска к работе:"
echo "Найдите на рабочем столе кнопку белого цвета с подписью \"tuktuk.desktop\"."
echo "Щелкните по ней правой клавишей мыши."
echo "Выбирите в меню пункт \"Разрешить запуск\" и отметьте его."
echo "Наслаждайтесь общением с друзьями."
