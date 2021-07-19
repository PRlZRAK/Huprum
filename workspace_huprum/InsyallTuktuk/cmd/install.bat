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
set reg_path=HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Explorer\Shell Folders
set reg_param=desktop
for /f "tokens=1,2,*" %%a in ('reg query "%reg_path%" /v "%reg_param%"') do if "%%a"=="%reg_param%" set reg_value=%%c
echo Set oWS = WScript.CreateObject("WScript.Shell") > CreateShortcut.vbs
echo sLinkFile = "%reg_value%\tuktuk.lnk" >> CreateShortcut.vbs
echo Set oLink = oWS.CreateShortcut(sLinkFile) >> CreateShortcut.vbs
echo oLink.TargetPath = "%USERPROFILE%\tuktuk\tuktuk.bat" >> CreateShortcut.vbs
echo oLink.IconLocation = "%USERPROFILE%\tuktuk\logo.ico" >> CreateShortcut.vbs
echo oLink.Save >> CreateShortcut.vbs
cscript CreateShortcut.vbs
del CreateShortcut.vbs
:: --------------