## To start mongo database 
<ul>
    <li>http://downloads.mongodb.org/win32/mongodb-win32-x86_64-2008plus-ssl-debugsymbols-4.0.2.zip</li>
    <li>mongod --dbpath=E:\mongodb-win32-x86_64-2008plus-ssl-4.0.2/mydb/</li>
</ul>

## Angular routing with Springboot thymeleaf
1. Create a 404.html in templates/error folder
<pre>
<!-- This file is being to handle angular routing problem while refreshing the page -->
&lt;html&gt;
    &lt;meta http-equiv="refresh" content="0;url=/" /&gt;
&lt;/html&gt;
</pre>

2. Create IndexController as following to register / path to serve index.html
<pre>
@Controller
public class IndexController {
    @RequestMapping(value = {"/"})
    public String index() {
        return "index";
    }
}
</pre>
## Start development server (angular)
If --aot=true is not provided then lazy routing wont work<br>
ng serve --aot=true

### Handy tips:
1. Add HttpClientModule in imports section to works with http
2. ngNativeValidate helps to prevent form submission if required field is not filled, usage e.g.  
<code>
    &lt;form (submit)="onSave()" name="addNewBook" ngNativeValidate&gt;
</code>

3. To use material date with locale (ar-SA)
<ul>
    <li>install npm i --save @angular/material-moment-adapter</li>
    <li>intsall npm i --save moment</li>
</ul>
Add the following modules:
MatDatepickerModule, MatMomentDateModule
Then add the providers in component:
import {MAT_MOMENT_DATE_FORMATS, MomentDateAdapter} from '@angular/material-moment-adapter';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material/core';
providers: [
    // The locale would typically be provided on the root module of your application. We do it at
    // the component level here, due to limitations of our example generation script.
    {provide: MAT_DATE_LOCALE, useValue: 'ar-SA'},

    // `MomentDateAdapter` and `MAT_MOMENT_DATE_FORMATS` can be automatically provided by importing
    // `MatMomentDateModule` in your applications root module. We provide it at the component level
    // here, due to limitations of our example generation script.
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},
    {provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS},
]

### Deploy angular application in springboot application
To remove directory while building application: npm install rimraf -g<br>
To create directory while building application: npm install mkdirp -g<br>
To copy application from dist into springboot static folder: npm i copyfiles -g<br>
Sample configuration : e.g. package.json
<pre>
"build": "ng build --prod --aot=true --base-href=/ui/ --deploy-url=/ui/",
"postbuild": "npm run deploy",
"predeploy": "rimraf ../resources/static/ui && mkdirp ../resources/static/ui",
"deploy": "copyfiles --up 2 dist/**/*.* ../resources/static/ui && copyfiles --up 2 dist/**/index.html ../resources/templates/ui/",
</pre>

### spring security
https://stackoverflow.com/questions/52753656/how-to-login-via-rest-api-using-reactiveuserdetailsservice-springboot

### Disable spring security while development
Add /** in last pathMatchers to bypass security
http.authorizeExchange().pathMatchers("/login","/signup","/api/users/login","/ui/assets/**","/webjars/**").permitAll()




