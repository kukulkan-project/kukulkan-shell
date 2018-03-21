productName: ${project.name}-ide
appId: mx.infotec.dads.kukulkan.${project.name}ide

asar: false
directories:
  buildResources: resources
files:
  - 'src-gen/**/*'
  - 'lib/**/*'
  - 'node_modules/**/*'
  - package.json

win:
  target:
    - nsis
mac:
  target:
    - dmg
linux:
  target:
    - deb
    - AppImage
  category: Development
  icon: resources/icons

nsis:
  menuCategory: true
  oneClick: false
  perMachine: true
  installerHeaderIcon: resources/icon.ico
  installerIcon: resources/icon.ico
  uninstallerIcon: resources/icon.ico
  installerSidebar: resources/installerSidebar.bmp
  uninstallerSidebar: resources/installerSidebar.bmp
  allowToChangeInstallationDirectory: true
  runAfterFinish: false
  <#noparse>artifactName: ${productName}-Installer-${version}.${ext}</#noparse>
dmg:
  background: resources/dmgInstaller.tiff
  icon: resources/icon.icns
  iconSize: 128
  contents:
    -
      x: 380
      y: 240
      type: link
      path: /Applications
    -
      x: 122
      y: 240
      type: file

publish:
  provider: github