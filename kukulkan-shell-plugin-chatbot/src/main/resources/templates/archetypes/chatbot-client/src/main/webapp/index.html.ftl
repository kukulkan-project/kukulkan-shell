<!doctype html>
<html class="no-js" lang="es" dir="ltr">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${project.id}</title>
    <meta name="description" content="">
    <meta name="google" value="notranslate">
    <meta name="theme-color" content="#000000">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <!-- build:css content/css/vendor.css -->
    <!-- bower:css -->
    <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.css">
    <link rel="stylesheet" href="bower_components/rdash-ui/dist/css/rdash.css">
    <link rel="stylesheet" href="bower_components/rdash-ui/dist/css/rdash.min.css">
    <link rel="stylesheet" href="bower_components/components-font-awesome/css/fontawesome-all.css">
    <link rel="stylesheet" href="bower_components/angular-loading-bar/build/loading-bar.css">
    <!-- endinject -->
    <!-- endbuild -->
    <!-- build:css content/css/main.css -->
    <link rel="stylesheet" href="content/css/main.css">
    <!-- endbuild -->
    <link rel="shortcut icon" href="favicon.ico" />
    <link rel="manifest" href="manifest.webapp" />
</head>

<body ng-app="${project.id}App" ng-strict-di>
    <!--[if lt IE 9]>
        <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
    <![endif]-->

    <page-ribbon></page-ribbon>
    <div id="page-wrapper">
    	<div ui-view="chatbot" ng-cloak></div>
        <div ui-view="navbar" ng-cloak></div>
        <div ui-view="headerbar" ng-cloak></div>

        <div id="content-wrapper">
            <div class="page-content">
                <div class="row">
                    <div class="col-md-12">
                        <div ui-view="content"></div>
                        <!-- Angular views -->
                        <div class="footer" ng-cloak>
                            <p data-translate="footer">This is your footer</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <noscript>
            <h1>You must enable javascript to view this page.</h1>
        </noscript>
    </div>

    <!-- uncomment this for adding service worker
        <script>
            if ('serviceWorker' in navigator) {
                 navigator.serviceWorker
                    .register('./sw.js')
                    .then(function() { console.log('Service Worker Registered'); });
            }
        </script>
    -->
    <!-- Google Analytics: uncomment and change UA-XXXXX-X to be your site's ID.
    <script>
        (function(b,o,i,l,e,r){b.GoogleAnalyticsObject=l;b[l]||(b[l]=
        function(){(b[l].q=b[l].q||[]).push(arguments)});b[l].l=+new Date;
        e=o.createElement(i);r=o.getElementsByTagName(i)[0];
        e.src='//www.google-analytics.com/analytics.js';
        r.parentNode.insertBefore(e,r)}(window,document,'script','ga'));
        ga('create','UA-XXXXX-X');ga('send','pageview');
    </script>-->

    <!-- build:js app/vendor.js -->
    <!-- bower:js -->
    <script src="bower_components/jquery/dist/jquery.js"></script>
    <script src="bower_components/messageformat/messageformat.js"></script>
    <script src="bower_components/json3/lib/json3.js"></script>
    <script src="bower_components/angular/angular.js"></script>
    <script src="bower_components/angular-aria/angular-aria.js"></script>
    <script src="bower_components/angular-bootstrap/ui-bootstrap-tpls.js"></script>
    <script src="bower_components/angular-cache-buster/angular-cache-buster.js"></script>
    <script src="bower_components/angular-cookies/angular-cookies.js"></script>
    <script src="bower_components/angular-dynamic-locale/src/tmhDynamicLocale.js"></script>
    <script src="bower_components/ngstorage/ngStorage.js"></script>
    <script src="bower_components/angular-loading-bar/build/loading-bar.js"></script>
    <script src="bower_components/angular-resource/angular-resource.js"></script>
    <script src="bower_components/angular-sanitize/angular-sanitize.js"></script>
    <script src="bower_components/angular-translate/angular-translate.js"></script>
    <script src="bower_components/angular-translate-interpolation-messageformat/angular-translate-interpolation-messageformat.js"></script>
    <script src="bower_components/angular-translate-loader-partial/angular-translate-loader-partial.js"></script>
    <script src="bower_components/angular-translate-storage-cookie/angular-translate-storage-cookie.js"></script>
    <script src="bower_components/angular-ui-router/release/angular-ui-router.js"></script>
    <script src="bower_components/bootstrap-ui-datetime-picker/dist/datetime-picker.js"></script>
    <script src="bower_components/ng-file-upload/ng-file-upload.js"></script>
    <script src="bower_components/ngInfiniteScroll/build/ng-infinite-scroll.js"></script>
    <!-- endinject -->
    <!-- endbuild -->

    <!-- build:js app/app.js  -->
    <!-- inject:js -->
    <script src="app/app.module.js"></script>
    <script src="app/services/user/user.service.js"></script>
    <script src="app/services/profiles/profile.service.js"></script>
    <script src="app/services/profiles/page-ribbon.directive.js"></script>
    <script src="app/services/auth/register.service.js"></script>
    <script src="app/services/auth/principal.service.js"></script>
    <script src="app/services/auth/password-reset-init.service.js"></script>
    <script src="app/services/auth/password-reset-finish.service.js"></script>
    <script src="app/services/auth/password.service.js"></script>
    <script src="app/services/auth/has-authority.directive.js"></script>
    <script src="app/services/auth/has-any-authority.directive.js"></script>
    <script src="app/services/auth/auth.service.js"></script>
    <script src="app/services/auth/auth.jwt.service.js"></script>
    <script src="app/services/auth/activate.service.js"></script>
    <script src="app/services/auth/account.service.js"></script>
    <script src="app/layouts/navbar/navbar.controller.js"></script>
    <script src="app/layouts/navbar/active-menu.directive.js"></script>
    <script src="app/layouts/headerbar/headerbar.controller.js"></script>
    <script src="app/layouts/error/error.state.js"></script>
    <script src="app/home/home.state.js"></script>
    <script src="app/home/home.controller.js"></script>
    <script src="app/entities/entity.state.js"></script>
    <script src="app/components/util/truncate-words.filter.js"></script>
    <script src="app/components/util/truncate-characters.filter.js"></script>
    <script src="app/components/util/sort-by.directive.js"></script>
    <script src="app/components/util/sort.directive.js"></script>
    <script src="app/components/util/parse-links.service.js"></script>
    <script src="app/components/util/pagination-util.service.js"></script>
    <script src="app/components/util/modal-draggable.directive.js"></script>
    <script src="app/components/util/jhi-item-count.directive.js"></script>
    <script src="app/components/util/error.constants.js"></script>
    <script src="app/components/util/date-util.service.js"></script>
    <script src="app/components/util/data-util.service.js"></script>
    <script src="app/components/util/capitalize.filter.js"></script>
    <script src="app/components/util/base64.service.js"></script>
    <script src="app/components/login/login.service.js"></script>
    <script src="app/components/login/login.controller.js"></script>
    <script src="app/components/language/language.service.js"></script>
    <script src="app/components/language/language.filter.js"></script>
    <script src="app/components/language/language.controller.js"></script>
    <script src="app/components/language/language.constants.js"></script>
    <script src="app/components/form/show-validation.directive.js"></script>
    <script src="app/components/form/pagination.constants.js"></script>
    <script src="app/components/form/minbytes.directive.js"></script>
    <script src="app/components/form/maxbytes.directive.js"></script>
    <script src="app/components/alert/alert-error.directive.js"></script>
    <script src="app/components/alert/alert.service.js"></script>
    <script src="app/components/alert/alert.directive.js"></script>
    <script src="app/blocks/interceptor/notification.interceptor.js"></script>
    <script src="app/blocks/interceptor/errorhandler.interceptor.js"></script>
    <script src="app/blocks/interceptor/auth-expired.interceptor.js"></script>
    <script src="app/blocks/interceptor/auth.interceptor.js"></script>
    <script src="app/blocks/handlers/translation.handler.js"></script>
    <script src="app/blocks/handlers/state.handler.js"></script>
    <script src="app/blocks/config/uib-pagination.config.js"></script>
    <script src="app/blocks/config/uib-pager.config.js"></script>
    <script src="app/blocks/config/translation-storage.provider.js"></script>
    <script src="app/blocks/config/translation.config.js"></script>
    <script src="app/blocks/config/localstorage.config.js"></script>
    <script src="app/blocks/config/http.config.js"></script>
    <script src="app/blocks/config/compile.config.js"></script>
    <script src="app/blocks/config/alert.config.js"></script>
    <script src="app/app.state.js"></script>
    <script src="app/app.constants.js"></script>
    <script src="app/admin/user-management/user-management-dialog.controller.js"></script>
    <script src="app/admin/user-management/user-management-detail.controller.js"></script>
    <script src="app/admin/user-management/user-management-delete-dialog.controller.js"></script>
    <script src="app/admin/user-management/user-management.state.js"></script>
    <script src="app/admin/user-management/user-management.controller.js"></script>
    <script src="app/admin/metrics/metrics.state.js"></script>
    <script src="app/admin/metrics/metrics.service.js"></script>
    <script src="app/admin/metrics/metrics.modal.controller.js"></script>
    <script src="app/admin/metrics/metrics.controller.js"></script>
    <script src="app/admin/logs/logs.state.js"></script>
    <script src="app/admin/logs/logs.service.js"></script>
    <script src="app/admin/logs/logs.controller.js"></script>
    <script src="app/admin/health/health.state.js"></script>
    <script src="app/admin/health/health.service.js"></script>
    <script src="app/admin/health/health.modal.controller.js"></script>
    <script src="app/admin/health/health.controller.js"></script>
    <script src="app/admin/docs/docs.state.js"></script>
    <script src="app/admin/configuration/configuration.state.js"></script>
    <script src="app/admin/configuration/configuration.service.js"></script>
    <script src="app/admin/configuration/configuration.controller.js"></script>
    <script src="app/admin/audits/audits.state.js"></script>
    <script src="app/admin/audits/audits.service.js"></script>
    <script src="app/admin/audits/audits.controller.js"></script>
    <script src="app/admin/admin.state.js"></script>
    <script src="app/account/settings/settings.state.js"></script>
    <script src="app/account/settings/settings.controller.js"></script>
    <script src="app/account/reset/request/reset.request.state.js"></script>
    <script src="app/account/reset/request/reset.request.controller.js"></script>
    <script src="app/account/reset/finish/reset.finish.state.js"></script>
    <script src="app/account/reset/finish/reset.finish.controller.js"></script>
    <script src="app/account/register/register.state.js"></script>
    <script src="app/account/register/register.controller.js"></script>
    <script src="app/account/password/password-strength-bar.directive.js"></script>
    <script src="app/account/password/password.state.js"></script>
    <script src="app/account/password/password.controller.js"></script>
    <script src="app/account/activate/activate.state.js"></script>
    <script src="app/account/activate/activate.controller.js"></script>
    <script src="app/account/account.state.js"></script>
    <!-- endinject -->
    <!-- endbuild -->

</body>

</html>