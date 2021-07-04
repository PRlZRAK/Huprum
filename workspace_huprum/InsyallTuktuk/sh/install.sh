#!/bin/bash
echo "1. инсталяция Java:"
echo "Проверка наличия Java в системе"
java -version
if [ $? -eq 0 ]
then
  echo "Java в наличии"
else
  echo "Java не установлена. Установить? yes/no"
  read -p " " otvet
  echo "вы ввели:" $otvet
  if [ "$otvet" == "yes" ]; then
    echo "Устанавливаем Java"
  else
    echo "Инсталяция без Java невозможна"
    exit 1
  fi	
fi
echo "2. Создание папки программы и копирование файлов:"
tukdir=$HOME/tuktuk
mkdir -p $tukdir
echo $tukdir
cp logo.png $tukdir
cp tuktuk.jar $tukdir
echo '#!/bin/bash' > $tukdir/tuktuk.sh
echo 'cd '$tukdir >> $tukdir/tuktuk.sh
echo 'java -jar tuktuk.jar' >> $tukdir/tuktuk.sh
chmod 777 $tukdir/tuktuk.sh
desktop="$(xdg-user-dir DESKTOP)"
echo $desktop

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
echo "конец"
