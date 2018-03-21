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

    //Generate the XText artifacts
    cd ${project.name}.parent && ./gradlew
    //Generate the Language Server and package into jar
    ./gradlew shadowJar
    //Install extension dependencies
    cd ..
    cd ${project.name}-extension && yarn install
    //Copy Language Server jar into extension
    yarn copy-ls 
    cd ..
    //Install dependencies
    yarn install
    //Recompile for electron
    yarn rebuild
    //Start the project
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