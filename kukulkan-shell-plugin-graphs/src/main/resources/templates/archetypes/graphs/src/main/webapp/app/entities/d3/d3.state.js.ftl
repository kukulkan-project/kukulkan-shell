(function() {
    'use strict';

    angular
        .module('${project.grammarName}App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('graphs', {
            parent: 'entity',
            url: '/graphs',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '${project.grammarName}.state.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/d3/d3.html',
                    controller: 'GraphsController',
                    controllerAs: 'vm'
                }
            },            
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        });
    }

})();
