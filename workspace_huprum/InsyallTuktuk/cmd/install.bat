echo on
java -version
if %ERRORLEVEL% EQU 0 goto ok
echo Install jre
jre-8u291-windows-i586-iftw.exe
:ok
chcp 1251
mkdir "%USERPROFILE%\tuktuk"
copy tuktuk.jar "%USERPROFILE%\tuktuk"
copy logo.ico "%USERPROFILE%\tuktuk"
echo @echo off >  "%USERPROFILE%\tuktuk\tuktuk.bat" 
echo chcp 1251 >>  "%USERPROFILE%\tuktuk\tuktuk.bat" 
echo cd "%USERPROFILE%\tuktuk" >>  "%USERPROFILE%\tuktuk\tuktuk.bat" 
echo start javaw -jar "%USERPROFILE%\tuktuk\tuktuk.jar" >>  "%USERPROFILE%\tuktuk\tuktuk.bat"
:: --------------
echo Set oWS = WScript.CreateObject("WScript.Shell") > CreateShortcut.vbs
echo sLinkFile = "%USERPROFILE%\Desktop\tuktuk.lnk" >> CreateShortcut.vbs
echo Set oLink = oWS.CreateShortcut(sLinkFile) >> CreateShortcut.vbs
echo oLink.TargetPath = "%USERPROFILE%\tuktuk\tuktuk.bat" >> CreateShortcut.vbs
echo oLink.IconLocation = "%USERPROFILE%\tuktuk\logo.ico" >> CreateShortcut.vbs
echo oLink.Save >> CreateShortcut.vbs
cscript CreateShortcut.vbs
del CreateShortcut.vbs
:: --------------

