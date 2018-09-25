## To start mongo database 
<ul>
    <li>http://downloads.mongodb.org/win32/mongodb-win32-x86_64-2008plus-ssl-debugsymbols-4.0.2.zip</li>
    <li>mongod --dbpath=E:\mongodb-win32-x86_64-2008plus-ssl-4.0.2/mydb/</li>
</ul>

## Start development server (angular)
If --aot=true is not provided then lazy routing wont work<br>
ng serve --aot=true

### Handy tips:
1. Add HttpClientModule in imports section to works with http
2. ngNativeValidate helps to prevent form submission if required field is not filled, usage e.g.  
<code>
    &lt;form (submit)="onSave()" name="addNewBook" ngNativeValidate&gt;
</code>





