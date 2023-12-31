# HotelBooking

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 16.1.3.

## Prepare environment
Install node
`apt install node`

Install Angular CLI  
```
npm install -g @angular/cli@
ng version // ran in 03.07.23
```
```
Package                         Version
---------------------------------------------------------
@angular-devkit/architect       0.1601.3
@angular-devkit/build-angular   16.1.3
@angular-devkit/core            16.1.3
@angular-devkit/schematics      16.1.3
@schematics/angular             16.1.3
rxjs                            7.8.1
typescript                      5.1.6
```

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

Alternatively use `npm run start`

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

## Testing

Please make sure that every component/service that you create has passing tests. Mock every service and import all the testing modules necessary to pass the initial "it should create" test generated by the CLI. Test the things that are integral for the UI experience. Test things that will break the whole app or destroy the UI/UX when bugs appear.

## Running unit tests

Run `ng test` or `npm run test` to execute the unit tests via [Karma](https://karma-runner.github.io).

The report can be inspected by looking at the index.html file in the coverage folder.

Current coverage (after running `npm run test`) - last ran 13.07.23:
```
=============================== Coverage summary ===============================       
Statements   : 80.85% ( 266/329 )
Branches     : 58.69% ( 54/92 )
Functions    : 85.39% ( 76/89 )
Lines        : 81.87% ( 253/309 )
```

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via a platform of your choice. To use this command, you need to first add a package that implements end-to-end testing capabilities.

## Code quality

To ensure a better quality in your written code then **please use** the **SonarLint** extension in your IDE. Pay attention to the problems that may be brought up by SonarLint. Definitely fix the SonarLint issues when you are finishing up with a new feature. Pull-requests obviously ignoring SonarLint issues (without an explanation) will be denied.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.
