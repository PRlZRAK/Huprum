echo on
java -version
if %ERRORLEVEL% EQU 0 goto ok
echo Install jre
jre-8u291-windows-i586-iftw.exe
:ok
mkdir %cd:~0,2%\tuktuk
copy tuktuk.jar %cd:~0,2%\tuktuk
copy logo.ico %cd:~0,2%\tuktuk
echo @echo off >  %cd:~0,2%\tuktuk\tuktuk.bat 
echo cd %cd:~0,2%\tuktuk >>  %cd:~0,2%\tuktuk\tuktuk.bat 
echo java -jar tuktuk.jar >>  %cd:~0,2%\tuktuk\tuktuk.bat
:: --------------
echo Set oWS = WScript.CreateObject("WScript.Shell") > CreateShortcut.vbs
echo sLinkFile = "..\tuktuk.lnk" >> CreateShortcut.vbs
echo Set oLink = oWS.CreateShortcut(sLinkFile) >> CreateShortcut.vbs
echo oLink.TargetPath = "%cd:~0,2%\tuktuk\tuktuk.bat" >> CreateShortcut.vbs
echo oLink.IconLocation = "%cd:~0,2%\tuktuk\logo.ico" >> CreateShortcut.vbs
echo oLink.Save >> CreateShortcut.vbs
cscript CreateShortcut.vbs
del CreateShortcut.vbs
:: --------------
pause

