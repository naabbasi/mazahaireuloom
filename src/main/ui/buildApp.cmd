@echo off
echo Please wait, application is being started
ng serve --base-href=/ --deploy-url=/ --host=0.0.0.0 --port=80 --disable-host-check -o --aot=true
REM ng build --base-href=/ui/ --deploy-url=/ui/ --prod --watch
