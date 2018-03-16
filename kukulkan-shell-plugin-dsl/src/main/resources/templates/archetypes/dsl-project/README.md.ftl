# The ${project.name} DSL project
The IDE for ${project.name} DSL

## Prerequisites

Install [nvm](https://github.com/creationix/nvm#install-script).

    curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.33.5/install.sh | bash

Install npm and node.

    nvm install 8
    nvm use 8

Install yarn.

    npm install -g yarn
    
## Quick Start


    cd ${project.name}-extension && yarn install
    cd ..
    yarn install
    yarn rebuild
    yarn start


## Packaging

For distributing your app, run the following command

    yarn package
    
This will generate:
- a `deb` and `AppImage` in Ubuntu
- an `exe` in Windows (you'll need some extra tools, see Windows subsection)
- an `dmg` in Mac

### Windows
- Download [MSBuild](https://www.visualstudio.com/es/downloads/)
- Add the environment variable `VCTargetsPath=C:\Program Files (x86)\MSBuild\Microsoft.Cpp\v4.0\v140` or the path where `Microsoft.Cpp.Default.props`
- Run `npm install --global --production windows-build-tools` as administrator
- Run `npm config set msvs_version 2017 --global`
 

## Creating the grammar
Create a XText project in root folder using the XText Eclipse plugin.

Follow this guidelines:
- Project name: `<YOUR PACKAGE>.${project.name}
- Location: <THIS FOLDER>`
- Name: `<YOUR PACKAGE>.${project.name}.${project.name?cap_first}`. Example: mx.infotec.dads.kukulkan.Kukulkan
- Extensions: ${project.extension}

**Click next**  

- Facets: 
- [x] Generic IDE Support
- [x] Testing Support

- Preferred Build System: **Gradle**
- Build Language Server: **Fat Jar**
- Source Layout: **Maven/Gradle**  

**Click Finish**

## Generating the language server

    cd <YOUR XText PROJECT>
    ./gradlew shadowJar

A jar will be generated in `<YOUR XText PROJECT>/<YOUR XText PROJECT>.ide/build/libs`.  

- Copy the relative jar path in the copy-ls script in `${project.name}-extension/package.json`

- Copy the relative jar path in `${project.name}-extension/node/${project.name}-backend-module.ts`

<% } %>
