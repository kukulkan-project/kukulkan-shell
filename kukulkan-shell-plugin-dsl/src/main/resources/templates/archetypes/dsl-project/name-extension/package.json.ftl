{
  "name": "${project.name}-extension",
  "keywords": [
    "theia-extension"
  ],
  "version": "${project.version}",
  "description": "The ${project.name} Theia extension",
  "author": "Kukulkan",
  <#if project.license?has_content>
  "license": "${project.license}",
  </#if>
  <#if project.githubUrl?has_content>
  "repository": {
    "type": "git",
    "url": "${project.githubUrl}.git"
  },
  "bugs": {
    "url": "${project.githubUrl}/issues"
  },
  "homepage": "${project.githubUrl}",
  </#if>
  "files": [
    "lib",
    "src"
  ],
  "dependencies": {
    "@theia/core": "${project.theiaVersion}"
  },
  "devDependencies": {
    "rimraf": "latest",
    "typescript": "latest"
  },
  "scripts": {
    "prepare": "yarn run clean && yarn run build",
    "copy-ls": "rm -rf ./build && mkdir build && cp ../${project.basePackage}.${project.name}.parent/${project.basePackage}.${project.name}.ide/build/libs/* ./build/dsl-ls.jar",
    "clean": "rimraf lib",
    "build": "tsc",
    "watch": "tsc -w"
  },
  "theiaExtensions": [
    {
      "backend": "lib/node/${project.name}-backend-module",
      "frontend": "lib/browser/${project.name}-frontend-module"
    }
  ]
}