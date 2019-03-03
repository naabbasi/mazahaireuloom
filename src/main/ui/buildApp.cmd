@echo off
echo Please wait, application is being started
ng serve --base-href=/ui/ --deploy-url=/ui/ --host=0.0.0.0 --port=80 --disable-host-check -o --aot=true
REM npm run-script buildDev
REM ng build --aot=true --base-href=/ui/ --deploy-url=/ui/
