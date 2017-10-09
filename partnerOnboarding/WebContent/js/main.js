/**
 * Configure the Routes
 */
var app = angular.module('LoginModule', [ 'ngRoute', 'ngCookies',
                                          'chieffancypants.loadingBar','ngAnimate','ngFlash','ngSanitize','ngTouch','NumericStepper','angularjs-datetime-picker']).value('dateFormat', { value: 'dd/MM/yyyy HH:mm:ss'} );
app
.run([
      '$http',
      '$cookieStore',
      '$rootScope',
      function($http, $cookieStore,$rootScope) {
    	  if ($cookieStore.get('globals')) {
    		  $http.defaults.headers.common['Authorization'] = 'Basic '
    				  + $cookieStore.get('globals').currentUser.authdata;
    		  $http.defaults.headers.common['Accept'] = 'application/json;odata=verbose';
    	  }

      } ]);
app.config([ '$routeProvider', 'cfpLoadingBarProvider',
             function($routeProvider, cfpLoadingBarProvider) {
	cfpLoadingBarProvider.includeSpinner = true;
	$routeProvider.when("/", {
		templateUrl : "html/LoginForm.html",
		controller : "PostController"
	}).when("/Title", {
		templateUrl : "html/Title.html",
		controller : "TitleController"
	}).when("/FlowSelection/:service", {
		templateUrl : "html/FlowSelection.html",
		controller : "FlowSelectionController"
	}).when("/NewPartner", {
		templateUrl : "html/NewPartner.html",
		controller : "PartnerInfoController"
	}).when("/PartnerInfo2", {
		templateUrl : "html/PartnerInfo2.html",
		controller : "PartnerInfoController"
	}).when("/EditPartner/:PartnerId", {
		templateUrl : "html/EditPartner.html",
		controller : "PartnerInfoController"
	}).when("/PartnerList", {
		templateUrl : "html/PartnerList.html",
		controller : "PartnerlistController"
	}).when("/CreateUser", {
		templateUrl : "html/CreateUser.html",
		controller : "UserLoginController"
	}).when("/EditUser/:userName", {
		templateUrl : "html/EditUser.html",
		controller : "EditUserController"
	}).when("/UserList", {
		templateUrl : "html/UserList.html",
		controller : "UserListController"
	}).when("/Upload", {
		templateUrl : "html/Upload.html",
		controller : "UploadController"
	}).when("/ManageConfig", {
		templateUrl : "html/ManageConfig.html",
		controller : "ManageConfigController"
	}).when("/ManageConfig/:config", {
		templateUrl : "html/ManageConfig.html",
		controller : "ManageConfigController"
	}).when("/StaticParameter", {
		templateUrl : "html/StaticParameter.html",
		controller : "StaticParameterController"
	}).when("/StaticParameter/:partnerId/:UserName/:CarrierCode", {
		templateUrl : "html/StaticParameter.html",
		controller : "StaticParameterController"
	}).when("/QueueServer", {
		templateUrl : "html/QueueServer.html",
		controller : "QueueServerController"
	}).when("/QueueServer/:serverName", {
		templateUrl : "html/QueueServer.html",
		controller : "QueueServerController"
	}).when("/CarrierConfig", {
		templateUrl : "html/CarrierConfig.html",
		controller : "CarrierConfigController"
	}).when("/CarrierConfig/:code", {
		templateUrl : "html/CarrierConfig.html",
		controller : "CarrierConfigController"
	}).when("/TrackingNumberValidation", {
		templateUrl : "html/TrackingNumberValidation.html",
		controller : "TrackingController"
	}).when("/EditTrackingNumberValidation/:carrierCode/:mask", {
		templateUrl : "html/TrackingNumberValidation.html",
		controller : "TrackingController"
	}).when("/FieldFormatList", {
		templateUrl : "html/EditFieldFormat.html",
		controller : "FieldFormatListController"
	}).when("/AddFieldFormats", {
		templateUrl : "html/AddFieldFormat.html",
		controller : "AddFieldFormatController"
	}).when("/DefaultTable", {
		templateUrl : "html/DefaultTable.html",
		controller : "FileFormatController"
	}).otherwise("/404", {
		templateUrl : "partials/404.html",
		controller : "PageCtrl"
	});
} ]);

app
.controller(
		'WelcomeController',
		[
		 '$cookieStore',
		 '$scope',
		 'AuthenticationService',
		 '$window',
		 '$rootScope',
		 function($cookieStore, $scope, AuthenticationService,
				 $window, $rootScope) {
			 $scope.header = {
					 url : "templates/header.html",
			 };
			 if ($cookieStore.get('globals')) {
				 $rootScope.loginUser = $cookieStore.get('globals').currentUser.userName;
				 if ($scope.loginUser != null
						 || $scope.loginUser != '') {
					 $rootScope.isUserLoggedIn = true;
				 }
			 } else {
				 $rootScope.isUserLoggedIn = false;
			 }
			 $scope.logout = function( ){
				 $rootScope.isUserLoggedIn = false;
				 AuthenticationService.ClearCredentials();
				 $window.location.href = '#/';
			 };

		 } ]);

app
.controller(
		'FlowSelectionController',
		[

		 '$scope',

		 '$routeParams',
		 function($scope, $routeParams) {
			 $scope.service = $routeParams.service;

		 } ]);


app.controller('QueueController', ['$scope', '$http', '$route','$rootScope', 'Flash',function($scope, $http,$route,$rootScope, Flash ){
	$http({
		method : 'GET',
		url : 'services/v1/partnerService/getQueueServers',
		headers : {
		'Content-Type' : 'application/json'
	}
	})
	.success(function(response) {
		$scope.queueServer = response;
	})
	.error(
			function(response) {
				Flash.create('danger', "Some Error occured while getting Partners Information");
			});
}]);





app.controller('UserLoginController', ['$scope', '$rootScope','$http', '$route','Flash',function($scope,$rootScope, $http,$route,Flash ){
	var userCtrl = this;
	$http({
		method : 'GET',
		url : 'services/v1/partnerService/getPartnersInfo',
		headers : {
		'Content-Type' : 'application/json'
	}
	})
	.success(function(response) {
		$scope.partners = response;
	})
	.error(
			function(response) {
				Flash.create('danger', "Some Error occured while getting User Information");
			});
	userCtrl.postForm = function() {
		var validationSuccess = true;
		if(!/^[\w]+$/.test(userCtrl.inputData.username))
		{validationSuccess = false;}
		if(userCtrl.inputData.username==undefined)
		{validationSuccess = false;}
		if(validationSuccess){
			var encodedString = '{"userName":"' + userCtrl.inputData.username+
					'","password":"' +
					userCtrl.inputData.password +
					'","role":"USER' +
					'","partnerId":' +
					userCtrl.inputData.partnerId+
					',"services":["' +
					userCtrl.inputData.services+
					'"]}';

			$http({
				method: 'POST',
				url: 'services/v1/partnerService/saveUser',
				data: encodedString,
				headers: {'Content-Type': 'application/json'}
			})
			.success(function(response) {

				Flash.create('success', "User Created successfully!");
				window.location.href='#/Title';		
			})		
			.error(function(data, status, headers, config) {
				Flash.create('danger', "Unable to save user");
			});}else{
				Flash.create('danger', "Username must not contain space...");
			}
	}

}]);


app.controller('EditUserController', ['$scope','$rootScope', '$http', '$route', '$routeParams','ApiService','Flash','Base64',
                                      function($scope,$rootScope, $http,$route,$routeParams,ApiService,Flash ,Base64){
	$scope.title = 'Edit User';

	$scope.serviceList = [{ "name":"EBT",
		"description" :"EBT"
	},
	                      { "name":"OD",
		"description" :"OD"},
	                      ];

	ApiService.GetUser($routeParams.userName, function(response){
		$scope.data = response;

		$scope.data.password= Base64.decode(response.password);

		$scope.selectedPartner = response.partner.partnerId;
		if($scope.data.services[0] !='')
		{
			$scope.selectedservice = $scope.data.services[0].name;
		}

	})

	ApiService.GetPartners(function(response){
		$scope.Partners = response;


	})

	// ridhima
	$scope.selectedModel = [];
	$scope.dropdownSettings = {
			scrollableHeight: '200px',
			scrollable: true,
			enableSearch: true,
			displayProp: 'name',
			idProp:'name'
	};


	$scope.title ='Edit User';
	$scope.postForm = function() {
		var encodedString = '{"userName":"' +$scope.data.userName+
				'","password":"' +
				$scope.data.password +
				'","role":"USER' +
				'","partnerId":' +
				$scope.selectedPartner+
				',"services":["' +
				$scope.selectedservice+
				'"]}';


		$http({
			method: 'POST',
			url: 'services/v1/partnerService/saveUser',
			data: encodedString,
			headers: {'Content-Type': 'application/json'}
		})
		.success(function(response) {
			Flash.create('success', "User updated successfully!");
			window.location.href='#/Title';		
		})		
		.error(function(data, status, headers, config) {
			Flash.create('danger', "Unable to save user");
		});

	}

}]);
app.controller(
		'PostController',
		[
		 '$scope',
		 '$http',
		 'AuthenticationService',
		 '$window',
		 '$location',
		 'cfpLoadingBar',
		 '$rootScope',
		 'Flash',
		 function($scope, $http, AuthenticationService, $window,
				 $location, cfpLoadingBar, $rootScope, Flash) {
			 $scope.start = function() {
				 cfpLoadingBar.start();
			 };

			 $scope.complete = function() {
				 cfpLoadingBar.complete();
			 }

			 AuthenticationService.ClearCredentials();
			 $rootScope.isUserLoggedIn = false;
			 this.postForm = function() {
				 $scope.start();
				 AuthenticationService
				 .Login(
						 this.userName,
						 this.password,
						 function(response) {
							 if (response.active == 1) {
								 AuthenticationService
								 .SetCredentials(
										 response.userName,
										 response.password);

								 $rootScope.isUserLoggedIn = true;
								 $rootScope.loginUser = response.userName
										 $window.location.href = '#/Title';
								 $scope.complete();
							 } else {
								 Flash.create('danger', "Invalid Credentials.Please Try Again");
								 $scope.complete();
							 }
						 });
			 };
		 } ]);

app.controller(
		'TitleController',
		[
		 '$cookieStore',
		 '$scope',
		 'AuthenticationService',
		 '$window',
		 '$location',
		 '$window',
		 function($cookieStore, $scope, AuthenticationService,
				 $window, $location,$window) {
			 if ($cookieStore.get('globals')) {
				 $scope.loginUser = $cookieStore.get('globals').currentUser.userName;
				 $scope.logout = function() {
					 AuthenticationService.ClearCredentials();
					 $window.location.href = '#/';
				 }
			 } else {
				 $window.location.href = '#/';

			 }
		 } ]);


app.controller(
		'ManageConfigController',['$scope','dateFormat','$routeParams','$window',
		                          function ($scope,dateFormat, $routeParams,$window) {

			this.dateFormat = dateFormat;
			var configValue =  $routeParams.config;
			if(configValue!= null)
			{
				$scope.confgType = configValue;
			}
			$scope.changeme = function() {

			}
		}]);

app.controller('StaticParameterController', 
		[
		 '$scope',
		 '$rootScope',
		 '$http',
		 'Flash',
		 '$routeParams',
		 'ApiService',
		 function($scope,$rootScope, $http,Flash,$routeParams,ApiService) {
			 var staticCtrl = this;
			 $scope.IsAdd = false;
			 ApiService.GetAllStaticParameters(function(response){
				 $scope.paramters = response;
			 }); 


			 ApiService.GetPartners(function(response){
				 $scope.Partners = response;

			 });

			 // Edit Mode
			 $scope.IsAdd = true;
			 staticCtrl.PrtId =  $routeParams.partnerId;


			 if(staticCtrl.PrtId!=null)
			 {
				 $scope.IsAdd = false;

				 ApiService.GetPartner(staticCtrl.PrtId, function( response){
					 $scope.selectedPartner = response.partnerId;


				 }); 

				 $scope.selectedUser = $routeParams.UserName;
				 $scope.selectedCarrier = $routeParams.CarrierCode;

				 ApiService.GetUsersByPartnerId($routeParams.partnerId ,function(response){
					 $scope.Users = response;
				 }) ;

				 ApiService.GetCarrierConfig(function(response){
					 $scope.Carriers = response;
				 }) ;


				 if(staticCtrl.PrtId==null || $scope.selectedUser==null|| $scope.selectedCarrier==null)
				 {
					 Flash.create('danger', "Partner or User or Carrier not selected");
				 }
				 else
				 {
					 ApiService.GetStaticParameters(staticCtrl.PrtId,$scope.selectedUser,$scope.selectedCarrier, function(response){
						 staticCtrl.inputData = response[0];
					 }); 
				 }
			 }

			 //Add Mode
			 staticCtrl.PartnerChanged = function(){

				 ApiService.GetUsersByPartnerId($scope.selectedPartner, function(response){
					 $scope.Users = response;
				 }) ;


			 } ;

			 staticCtrl.UserChanged = function(){
				 ApiService.GetCarrierConfig(function(response){
					 $scope.Carriers = response;
				 }) ;
			 };
			 staticCtrl.CarrierChanged = function(){

				 if($scope.selectedPartner==null)
				 {
					 $scope.selectedPartner="null";
				 }
				 if($scope.selectedUser==null)
				 {
					 $scope.selectedUser="null";
				 }
				 if($scope.selectedCarrier==null)
				 {
					 $scope.selectedCarrier="null";
				 }
				 if($scope.selectedPartner==null)
				 {
					 ApiService.GetAllStaticParameters(function(response){
						 staticCtrl.inputData = response;
					 }); 
				 }
				 else
				 {
					 ApiService.GetStaticParameters($scope.selectedPartner,$scope.selectedUser,$scope.selectedCarrier, function(response){
						 staticCtrl.inputData = response;
					 });
				 }
			 };
			 staticCtrl.postForm = function() {
				 var validationSuccess = true;
				 if(!/^[\w]+$/.test(staticCtrl.inputData.name))
				 {validationSuccess = false;}
				 if(staticCtrl.inputData.name==undefined)
				 {validationSuccess = false;}
				 if(staticCtrl.inputData.value==undefined)
				 {validationSuccess = false;}
				 if(!/^[\w]+$/.test(staticCtrl.inputData.value)){
					 {validationSuccess = false;}
				 }

				 if(validationSuccess){
					 var encodedString = '{"partnerId":"'
							 + $scope.selectedPartner
							 +'","userName":"'
							 + $scope.selectedUser
							 +'","carrierCode":"'
							 + $scope.selectedCarrier
							 + '","name":"'
							 + staticCtrl.inputData.name
							 + '","value": "'
							 + staticCtrl.inputData.value + '"}';


					 encodedString = encodedString.replace("/\/", "\\"); 

					 $http({
						 method : 'POST',
						 url : 'services/v1/partnerService/saveStaticParameter',
						 data : encodedString,
						 headers : {
						 'Content-Type' : 'application/json'
					 }
					 }).success(
							 function(data, status, headers, config, response) {
								 console.log(data);
								 if (status == 200) {
									 Flash.create('success', "Static Parameter Created successfully!");
									 window.location = "#/ManageConfig";
								 }
							 });}else
								 Flash.create('danger', "Name or Value must not contain space !!!!");
			 }
			 $scope.editStaticParameter = function(partnerId, userName,carrierCode) {
				 window.location.href='#/StaticParameter/'+partnerId+'/'+userName+'/'+carrierCode;
			 }

			 $scope.addStaticParameter = function() {
				 window.location.href='#/StaticParameter';
			 }

			 $scope.deleteStatic = function(partnerId) {
				 $http(
						 {
							 method : 'GET',
							 url : 'services/v1/partnerService/deleteStaticParameter/'
								 + partnerId,
								 headers : {
							 'Content-Type' : 'application/json'
						 }
						 })
				 .success(function(response) {

					 $scope.paramters = response;
				 })
				 .error(
						 function(response) {
							 Flash.create('danger', "Some error occurred while deleting static parameter");
						 });
			 }

		 } ]);

app.controller('QueueServerController',
		[
		 '$scope',
		 '$rootScope',
		 '$http',
		 'Flash',
		 '$routeParams',
		 'ApiService',
		 function($scope,$rootScope, $http,Flash,$routeParams,ApiService) {
			 var queueCtrl = this;
			 $scope.IsAdd = true;
			 queueCtrl.sName = $routeParams.serverName;

			 if(queueCtrl.sName!=null)
			 {
				 $scope.IsAdd = false;
				 ApiService.getQueueServersByServerName(queueCtrl.sName, function(response){
					 queueCtrl.inputData = response;
				 });
			 }
			 $http({
				 method : 'GET',
				 url : 'services/v1/partnerService/getQueueServers',
				 headers : {
				 'Content-Type' : 'application/json'
			 }
			 })
			 .success(function(response) {
				 $scope.queueServer = response;
			 })
			 .error(
					 function(response) {
					 });

			 this.postForm = function() {
				 var validationSuccess = true;
				 if(!/^[\w]+$/.test(this.inputData.serverName))
				 {validationSuccess = false;}
				 if((this.inputData.serverName==undefined))
				 {validationSuccess = false;}
				 if((this.inputData.queueServer==undefined))
				 {validationSuccess = false;}
				 if(!/^[\w]+$/.test(this.inputData.queueServer))
				 {validationSuccess = false;}

				 if(validationSuccess){
					 var encodedString = '{"serverName":"'
							 +this.inputData.serverName
							 + '","queueServer":"'
							 + this.inputData.queueServer + '"}';

					 $http({
						 method : 'POST',
						 url : 'services/v1/partnerService/saveQueueServers',
						 data : encodedString,
						 headers : {
						 'Content-Type' : 'application/json'
					 }
					 }).success(
							 function(data, status, headers, config, response) {
								 console.log(data);
								 if (status == 200) {
									 Flash.create('success', "Queue server Created successfully!");
									 window.location = "#/ManageConfig";
								 }
							 }).error(function(data, status, headers, config ,http) {
								 Flash.create('danger', "Unable to submit form");
							 });}else{
								 Flash.create('danger', "Queue server name and value must not contain space.....");
							 }
			 }
			 $scope.editQueueServer = function(serverName) {
				 window.location.href='#/QueueServer/'+serverName;
			 }

			 $scope.addQueueServer = function(serverName) {
				 window.location.href='#/QueueServer'
			 }

			 $scope.deleteQueueServer = function(serverName) {
				 $http(
						 {
							 method : 'GET',
							 url : 'services/v1/partnerService/deleteQueueServer/'
								 + serverName,
								 headers : {
							 'Content-Type' : 'application/json'
						 }
						 })
				 .success(function(response) {

					 $scope.queueServer = response;
				 })
				 .error(
						 function(response) {
							 Flash.create('danger', "Some error occurred while deleting queue server");
						 });
			 }} ]);
app.controller('CarrierConfigController', 
		[
		 '$scope',
		 '$rootScope',
		 '$http',
		 '$filter',
		 'Flash',
		 '$routeParams',
		 'ApiService',
		 'dateFormat',
		 function($scope, $rootScope,$http,$filter,Flash,$routeParams,ApiService,dateFormat) {
			 var carrierCtrl = this;

			 carrierCtrl.dateTimeFormat = dateFormat.value;

			 carrierCtrl.serviceTypeList = [{ "serviceType":"API", 
			 },{ "serviceType":"FILE"

			 }
			 ];

			 $scope.IsAdd = true;
			 carrierCtrl.code = $routeParams.code;

			 if(carrierCtrl.code!=null)
			 {
				 $scope.IsAdd = false;
				 ApiService.getCarrierConfigByCode(carrierCtrl.code, function(response){
					 carrierCtrl.inputData = response;

					 carrierCtrl.inputData.startDownTime = $filter('date')(carrierCtrl.inputData.startDownTime, carrierCtrl.dateTimeFormat, 'UTC');
					 carrierCtrl.inputData.endDownTime = $filter('date')(carrierCtrl.inputData.endDownTime, carrierCtrl.dateTimeFormat, 'UTC');
					 carrierCtrl.selectedServiceType = response.serviceType;

				 });
			 }
			 $http({
				 method : 'GET',
				 url : 'services/v1/partnerService/getCarrierConfig',
				 headers : {
				 'Content-Type' : 'application/json'
			 }
			 })
			 .success(function(response) {
				 $scope.carrier = response;
			 })
			 .error(
					 function(response) {
					 });

			 $scope.datePattern=/^$|^((0[1-9]|[12][0-9]|3[01])\/(0[1-9]|1[012])\/(2\d{3})\s([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9]))$/;

			 carrierCtrl.postForm = function() {

				 var carrierConfigExists = false;

				 angular.forEach($scope.carrier, function(carrier, iterator){

					 if(carrier.code == carrierCtrl.inputData.code && $scope.IsAdd)
					 {
						 carrierConfigExists = true;
					 }
				 });

				 if(!carrierConfigExists)     
				 {
					 if(carrierCtrl.inputData.startDownTime != null){
						 if($scope.datePattern.test(carrierCtrl.inputData.endDownTime) && $scope.datePattern.test(carrierCtrl.inputData.startDownTime))
						 {
							 if($scope.carrierConfigForm.$valid)
							 {
								 var validationSuccess = true;
								 if(!/^[\w]+$/.test(carrierCtrl.inputData.code))
								 {validationSuccess = false;}
								 if((carrierCtrl.inputData.code==undefined))
								 {validationSuccess = false;}
								 if((carrierCtrl.inputData.name==undefined))
								 {validationSuccess = false;}
								 if(!/^[\w]+$/.test(carrierCtrl.inputData.name))
								 {validationSuccess = false;}
								 if(validationSuccess){
									 var encodedString = '{"code":"'
											 + carrierCtrl.inputData.code
											 +'","name":"'
											 + carrierCtrl.inputData.name
											 +'","serviceType":"'
											 + carrierCtrl.selectedServiceType
											 +'","startDownTime":"'
											 + $filter('date','year')(carrierCtrl.inputData.startDownTime  , carrierCtrl.dateTimeFormat) 
											 + '","endDownTime":"'
											 + $filter('date','year')(carrierCtrl.inputData.endDownTime  , carrierCtrl.dateTimeFormat) +'"}';

									 $http({
										 method : 'POST',
										 url : 'services/v1/partnerService/saveCarrierConfig',
										 data : encodedString,
										 headers : {
										 'Content-Type' : 'application/json'
									 }
									 }).success(
											 function(data, status, headers, config, response) {
												 console.log(data);
												 if (status == 200) {
													 Flash.create('success', "Carrier configuration Created successfully!");
													 window.location = "#/ManageConfig";
												 }
											 }).error(function(data, status, headers, config ,http) {
												 Flash.create('danger', "End Date should greater than Start date");
											 });}else{
												 Flash.create('danger', "code and name must not contain space....");
											 }
							 }
							 else
							 {
								 Flash.create('danger', "StartDate or EndDate invalid");
							 }

						 }}else
							 if($scope.carrierConfigForm.$valid)
							 {
								 var validationSuccess = true;
								 if(!/^[\w]+$/.test(carrierCtrl.inputData.code))
								 {validationSuccess = false;}
								 if(carrierCtrl.inputData.code==undefined)
								 {validationSuccess = false;}
								 if(carrierCtrl.inputData.name==undefined)
								 {validationSuccess = false;}
								 if(!/^[\w]+$/.test(carrierCtrl.inputData.name))
								 {validationSuccess = false;}

								 if(validationSuccess){
									 var encodedString = '{"code":"'
											 + carrierCtrl.inputData.code
											 +'","name":"'
											 + carrierCtrl.inputData.name
											 +'","serviceType":"'
											 + carrierCtrl.selectedServiceType
											 +'","startDownTime":"'
											 + "null"
											 + '","endDownTime":"'
											 + null +'"}';

									 $http({
										 method : 'POST',
										 url : 'services/v1/partnerService/saveCarrierConfig',
										 data : encodedString,
										 headers : {
										 'Content-Type' : 'application/json'
									 }
									 }).success(
											 function(data, status, headers, config, response) {
												 console.log(data);
												 if (status == 200) {
													 Flash.create('success', "Carrier configuration Created successfully!");
													 window.location = "#/ManageConfig";
												 }
											 }).error(function(data, status, headers, config ,http) {
												 Flash.create('danger', "End date should be greater than the start date.. ");
											 });}else{
												 Flash.create('danger', "code and name must not contain space....");
											 }
							 }
							 else{
								 Flash.create('danger', "Start or end datetime is in incorrect format!");
							 }}
				 else{
					 Flash.create('danger', "Configuration for this carrier code already exists!");
				 }
			 }

			 carrierCtrl.back = function(){
				 window.history.back();
			 };   

			 $scope.addCarrierConfig = function() {
				 window.location.href='#/CarrierConfig/';
			 }

			 $scope.editCarrierConfig = function(code) {
				 window.location.href='#/CarrierConfig/' + code;
			 }
			 $scope.deleteCarrierConfig = function(code) {
				 $http(
						 {
							 method : 'GET',
							 url : 'services/v1/partnerService/deleteCarrierConfig/'
								 + code,
								 headers : {
							 'Content-Type' : 'application/json'
						 }
						 })
				 .success(function(response) {

					 $scope.carrier = response;
				 })
				 .error(
						 function(response) {
							 Flash.create('danger', "Some error occurred while deleting carrier config entry");
						 });
			 } 


		 }]);



app.controller('TrackingController', 
		[
		 '$scope',
		 '$rootScope',
		 '$http',
		 'Flash',
		 '$routeParams',
		 'ApiService',
		 function($scope,$rootScope, $http,Flash,$routeParams,ApiService) {

			 var trackingCtrl = this;

			 ApiService.GetCarrierConfig(function(response){
				 $scope.CarrierCodes = response;
			 });

			 trackingCtrl.carrierCode = $routeParams.carrierCode;
			 trackingCtrl.mask = $routeParams.mask;
			 $scope.IsAdd = true;
			 if(trackingCtrl.carrierCode!=null)
			 {
				 $scope.IsAdd = false;
				 ApiService.getTrackingNumberValidationByCarrierCode(trackingCtrl.carrierCode,trackingCtrl.mask, function(response){
					 trackingCtrl.inputData=response;
				 });
			 }
			 $http({
				 method : 'GET',
				 url : 'services/v1/partnerService/getTrackingNumberValidation',
				 headers : {
				 'Content-Type' : 'application/json'
			 }
			 })
			 .success(function(response) {
				 $scope.tracking = response;
			 })
			 .error(
					 function(response) {
						 Flash.create('danger', "Some error occurred while listing Regex");
					 });
			 
			 
			 trackingCtrl.postForm = function() {
				 var validationSuccess = true;
				 if(!/^[\w]+$/.test(trackingCtrl.inputData.carrierCode))
				 {validationSuccess = false;}
				 if(trackingCtrl.inputData.carrierCode==undefined)
				 {validationSuccess = false;}

				 if(validationSuccess){
					 var encodedString = '{"carrierCode":"'
							 + trackingCtrl.inputData.carrierCode
							 + '","mask":"'
							 + this.inputData.mask + '"}';
					 var double="\\\\";
					 var res = encodedString.split('\\').join(double); 


					 $http({
						 method : 'POST',
						 url : 'services/v1/partnerService/saveTrackingNumberValidation',
						 data : res,
						 headers : {
						 'Content-Type' : 'application/json'
					 }
					 }).success(
							 function(data, status, headers, config, response, http) {
								 console.log(data);
								 if (status == 200) {
									 Flash.create('success', "Tracking number validation Created successfully!");
									 window.location = "#/ManageConfig";
								 }
							 }).error(function(data, status, headers, config) {
								 Flash.create('danger', "Unable to submit form. Please check the Mask value before saving.");
							 });}else{
								 Flash.create('danger', "code must not contain space....");
							 }
			 }
			 $scope.editTracking = function(carrierCode,mask) {

				 window.location.href='#/EditTrackingNumberValidation/'+ carrierCode+'/'+encodeURIComponent(mask);
			 }
			 $scope.editTrackingAndSave = function() {
				 var encodedString = '{"carrierCode":"'
						 + trackingCtrl.inputData.carrierCode
						 + '","oldMask":"'
						 + trackingCtrl.mask
						 + '","newMask":"'
						 + trackingCtrl.inputData.mask + '"}';
				 var double="\\\\";
				 var res = encodedString.split('\\').join(double); 


				 $http({
					 method : 'POST',
					 url : 'services/v1/partnerService/saveEditTrackingNumberValidation',
					 data : res,
					 headers : {
					 'Content-Type' : 'application/json'
				 }
				 }).success(
						 function(data, status, headers, config, response, http) {
							 console.log(data);
							 if (status == 200) {
								 Flash.create('success', "Tracking number validation Created successfully!");
								 window.location = "#/ManageConfig";
							 }
						 }).error(function(data, status, headers, config) {
							 Flash.create('danger', "Unable to submit form. Please check the Mask value before saving.");
						 });

			 }
			 $scope.addTrackingAndSave = function() {
				 
				 
				 var encodedString = '{"carrierCode":"'
						 + trackingCtrl.inputData.carrierCode
						 + '","mask":"'
						 + trackingCtrl.inputData.mask + '"}';
				 var double="\\\\";
				 var res = encodedString.split('\\').join(double); 


				 $http({
					 method : 'POST',
					 url : 'services/v1/partnerService/saveTrackingNumberValidation',
					 data : res,
					 headers : {
					 'Content-Type' : 'application/json'
				 }
				 }).success(
						 function(data, status, headers, config, response, http) {
							 console.log(data);
							 if (status == 200) {
								 Flash.create('success', "Tracking number validation Created successfully!");
								 window.location = "#/ManageConfig";
							 }
						 }).error(function(data, status, headers, config) {
							 Flash.create('danger', "Unable to submit form. Please check the Mask value before saving.");
						 });

			 }


			 $scope.addTracking = function(carrierCode) {
				 window.location.href='#/TrackingNumberValidation/';
			 }

			 $scope.deleteTracking = function(carrierCode) {
				 $http(
						 {
							 method : 'GET',
							 url : 'services/v1/partnerService/deleteTrackingNumberValidation/'
								 + carrierCode,
								 headers : {
							 'Content-Type' : 'application/json'
						 }
						 })
				 .success(function(response) {

					 $scope.tracking = response;
				 })
				 .error(
						 function(response) {
							 Flash.create('danger', "Some error occurred while deleting Regex");
						 });
			 }
		 }]);


app.controller('PartnerInfoController', 
		[
		 '$scope',
		 '$rootScope',
		 '$http',
		 '$routeParams',
		 'AuthenticationService',
		 'ApiService',
		 'Flash',
		 function($scope,$rootScope, $http,$routeParams, AuthenticationService,ApiService,Flash) {
			 var postCtrl = this;
			 $scope.countryCodeList = [{ "id":"2",
				 "name": "ISO2" },
			                           { "id":"3",
					 "name": "ISO3" },
			                           { "id":"4",
						 "name": "ISO4" } ];
			 var isAdd = true;
			 if($routeParams.PartnerId != null)
			 {
				 isAdd= false;
				 ApiService.GetPartner($routeParams.PartnerId, function(response){

					 postCtrl.inputData =        response;
					 if($scope.countryCodeList!=null && postCtrl.inputData!=null && postCtrl.inputData.countryCode!=null)
					 {
						 for (i = 0; $scope.countryCodeList.length > i; i += 1) {
							 if ($scope.countryCodeList[i].id == postCtrl.inputData.countryCode) {
								 $scope.selectedCountryCode = $scope.countryCodeList[i].id;
							 }
						 }
					 }                                                 
				 }); 
			 }

			 ApiService.GetPartners(function(response){
				 $scope.Partners = response;

			 })


			 postCtrl.reportScanList = ["SummaryOnly", "SummaryAndMostRecentDetail","SummaryAndDetails"]


					 postCtrl.postForm = function() {
				 var validationSuccess = true;
				 if(!/^[\w-]+$/.test(postCtrl.inputData.partnerCode))
				 {validationSuccess = false;}
				 if(postCtrl.inputData.partnerCode==undefined)
				 {validationSuccess = false;}
				 if(postCtrl.inputData.name==undefined)
				 {validationSuccess = false;}
				 if(!/^[\w]+$/.test(postCtrl.inputData.name))
				 {validationSuccess = false;}

				 var partnerExists = false;

				 angular.forEach($scope.Partners, function(partner, iterator){

					 if(partner.partnerId ==postCtrl.inputData.partnerId  && isAdd)
					 {
						 partnerExists = true;
					 }
				 });

				 if(validationSuccess)
				 {
					 if(!partnerExists )
					 {
						 $scope.partnerCode = postCtrl.inputData.name;
						 var encodedString = '{"partnerId":"'
								 + postCtrl.inputData.partnerId
								 +'","partnerCode":"'
								 + postCtrl.inputData.partnerCode
								 +'","countryCode":"'
								 + $scope.selectedCountryCode
								 + '","name":"'
								 + postCtrl.inputData.name
								 + '","fileExt":".txt'
								 + '","fileNamePattern":"'
								 + postCtrl.inputData.fileNamePattern
								 + '","delimiter":"~'

								 + '","header":"false'
								 + '","blankFile":"false'
								 + '","maxSize":"0'
								 + '","frequency":"0'
								 + '","reportScan":"'
								 + postCtrl.inputData.reportScan

								 + '"}';

						 $http({
							 method : 'POST',
							 url : 'services/v1/partnerService/savePartnerInfo',
							 data : encodedString,
							 headers : {
							 'Content-Type' : 'application/json'
						 }
						 }).success(
								 function(data, status, headers, config, response) {
									 console.log(data);
									 if (status == 200) {
										 AuthenticationService.SetSession('partnerId',
												 data.partnerId);
										 Flash.create('success', "Partner Created or updated successfully!");
										 window.location = "#/Title";
									 }
								 }).error(function(data, status, headers, config) {
									 Flash.create('danger', "Unable to submit form");
								 });
					 }
					 else{

						 Flash.create('danger', "Partner with this name already exists. Please edit partner.");
					 }
				 }
				 else
				 {
					 Flash.create('danger', "Partner code can  contain alphabets, numbers , - and _ . ");
					 Flash.create('danger', "Name must not contain space");
				 }
			 }

		 } ]);

app.controller('PostController3', 
		[
		 '$scope',
		 '$rootScope',
		 '$http',
		 '$cookieStore',
		 'Flash',
		 function($scope, $rootScope,$http, $cookieStore,Flash) {

			 this.postForm = function() {

				 var encodedString = '{"partnerId":"'
						 + $cookieStore.get('partnerId')
						 + '","fileTransferDetailRequest":{"host":"'
						 + this.inputData.host
						 + '","port":"'
						 + this.inputData.port
						 + '","protocol":"'
						 + this.inputData.protocol
						 + '","userName":"'
						 + this.inputData.userName
						 + '","password":"'
						 +this.inputData.password + '"}}';
				 console.log(encodedString);
				 $http({
					 method : 'POST',
					 url : 'services/v1/partnerService/savePartnerInfo',
					 data : encodedString,
					 headers : {
					 'Content-Type' : 'application/json'
				 }
				 }).success(function(data, status, headers, config) {
					 if (status == 200) {
						 Flash.create('success', "Partner updated successfully!");
						 window.location = "#/Title";
					 }
				 }).error(function(data, status, headers, config) {
					 Flash.create('danger', "Unable to submit form");
				 })
			 }

		 } ]);
app.controller('EditFileController', 
		[
		 '$scope',
		 '$rootScope',
		 '$http',
		 '$cookieStore',
		 'Flash',
		 function($scope,$rootScope, $http, $cookieStore,Flash) {

			 this.postForm = function() {
				 var encodedString = '{"name":"'
						 + $cookieStore.get('partnerCode')
						 + '","fileTransferDetailRequest":{"host":"'
						 + this.inputData.host
						 + '","port":"'
						 + this.inputData.port
						 + '","protocol":"'
						 + this.inputData.protocol
						 + '","userName":"'
						 +this.inputData.userName
						 + '","password":"'
						 +this.inputData.password + '"}}';
				 $http({
					 method : 'POST',
					 url : 'services/v1/partnerService/savePartnerInfo',
					 data : encodedString,
					 headers : {
					 'Content-Type' : 'application/json'
				 }
				 }).success(function(data, status, headers, config) {
					 if (status == 200) {
						 Flash.create('success', "User Created successfully!");
						 window.location = "#/Title";
					 }
				 }).error(function(data, status, headers, config) {
					 Flash.create('danger', "Unable to submit form");
				 })
			 }

		 } ]);
app.controller(
		'PartnerlistController',
		[
		 '$scope',
		 '$rootScope',
		 '$http',
		 '$document',
		 'Flash',
		 function($scope,$rootScope, $http, $document,Flash) {
			 $scope.roles = [ {
				 'role' : 'Admin'
			 }, {
				 'role' : 'User'
			 } ]
					 $http(
							 {
								 method : 'GET',
								 url : 'services/v1/partnerService/getPartnersInfo',
								 headers : {
								 'Content-Type' : 'application/json'
							 }
							 })
					 .success(function(response) {
						 $scope.myData = response;
					 })
					 .error(
							 function(response) {
								 Flash.create('danger', "Some Error occured while getting Partners Information");
							 });

			 $scope.deletePartner = function(id) {
				 $http(
						 {
							 method : 'GET',
							 url : 'services/v1/partnerService/deletePartnerInfo/'
								 + id,
								 headers : {
							 'Content-Type' : 'application/json'
						 }
						 })
				 .success(function(response) {
					 $scope.myData = response;
				 })
				 .error(
						 function(response) {
							 Flash.create('danger', "Some Error occurred while trying to delete "+id);
						 })
			 }
			 $scope.editPartner = function(id) {
				 window.location.href='#/EditPartner/'+id;
			 }
			 $scope.editPassword = function(password) {
				 var users = document
						 .getElementsByName("userName");
				 for (var x = 0; x < users.length; x++) {
					 users[x].setAttribute('readonly', true);
				 }
				 document.getElementById(active)
				 .removeAttribute('readonly');
			 }
			 $scope.saveUser = function(userName, role, password) {
			 }

		 } ]);
app.directive('numbersOnly', function() {
	return {
		require : 'ngModel',
		link : function(scope, element, attrs, modelCtrl) {
		modelCtrl.$parsers.push(function(inputValue) {
			if (inputValue == undefined)
				return ''
						var transformedInput = inputValue.replace(/[^0-9]/g, '');
			if (transformedInput != inputValue) {
				modelCtrl.$setViewValue(transformedInput);
				modelCtrl.$render();
			}
			return transformedInput;
		});
	}
	};
});
app.controller(
		'UserListController',
		[
		 '$scope',
		 '$rootScope',
		 '$http',
		 '$document',
		 '$timeout',
		 '$rootScope',
		 '$anchorScroll',
		 'ApiService',
		 'Flash',
		 function($scope,$rootScope, $http, $document, $timeout, $rootScope,$anchorScroll,ApiService,Flash) {
			 $scope.roles = [ {
				 'role' : 'Admin'
			 }, {
				 'role' : 'User'
			 } ];

			 ApiService.GetUsers(function(response){

				 $scope.myData = response;
			 }); 
			 $scope.jumpToLocation = function(){
				 $anchorScroll();
			 }



			 $scope.editUser = function(name) {
				 window.location.href='#/EditUser/'+name;
			 }
			 $scope.deleteUser = function(userName) {
				 $http(
						 {
							 method : 'GET',
							 url : 'services/v1/partnerService/deleteUser/'
								 + userName,
								 headers : {
							 'Content-Type' : 'application/json'
						 }
						 })
				 .success(function(response) {

					 $scope.myData = response;
					 $rootScope.successMsg = "User Deleted successfully!";
				 })
				 .error(
						 function(response) {
							 Flash.create('danger', "Some error occurred while deleting User");
						 })
			 }
			 $scope.editPassword = function(password, element) {
				 var passwords = document
						 .getElementsByName("password");

				 for (var x = 0; x < passwords.length; x++) {
					 passwords[x].setAttribute('readonly', true);
				 }
				 document.getElementById(password)
				 .removeAttribute('readonly')
			 }
			 $scope.saveUser = function(userName, role, password) {
			 }
		 } ]);




app.controller(
		'FieldFormatListController',
		[
		 '$scope',
		 '$rootScope',
		 '$routeParams',
		 'ApiService',
		 'Flash',
		 function($scope,$rootScope,$routeParams, ApiService,Flash) {




			 var fflctrl = this;
			 fflctrl.formats = [{'id':'0',

				 'value' : 'dd/MM/yyyy'},
			                    {'id':'1',

					 'value' : 'dd-MM-yyyy'},
			                    {'id':'2',

						 'value' : 'hh:mm:ss'},
			                    {'id':'3',

							 'value' : 'string'}]


									 ApiService.GetPartners(function(response){
										 $scope.Partners = response;
									 });

			 if($routeParams.partnerId !=null)
			 {
				 ApiService.GetFileFormatByPartnerId($routeParams.partnerId, function(response){
					 $scope.data = response; 




					 if($scope.data==null)
						 Flash.create('danger', "no field formats exists for this partner"); 

				 });

			 }




			 fflctrl.PartnerChanged = function(){

				 ApiService.GetFileFormatByPartnerId($scope.selectedPartner, function(response){
					 $scope.data = response; 

					 if(fflctrl.formats!=null && $scope.data!=null)
					 {
						 angular.forEach($scope.data, function(data, iterator){
							 for(counter=0;counter< fflctrl.formats.length;counter+=1)
							 {
								 if(fflctrl.formats[counter].value === data.format)
								 {
									 data.format = {'id' : fflctrl.formats[counter].id, value : fflctrl.formats[counter].value  }
								 }
							 }
						 });
					 }

					 $scope.model = { fieldFormats:$scope.data,
							 selected:{}

					 }
					 if($scope.data=="")
						 Flash.create('danger', "no field formats exists for this partner"); 

				 });

			 } ; 



			 fflctrl.getTemplate = function (fieldFormat) {
				 if ($scope.model.selected && fieldFormat.columnName === $scope.model.selected.columnName) return 'edit';
				 else return 'display';
			 };

			 fflctrl.editFieldFormats = function(fieldFormat){
				 $scope.model.selected = angular.copy(fieldFormat);
				 if($scope.model.selected.required == true)
				 {
					 $scope.model.selected.required = 1;
				 }
				 else{
					 $scope.model.selected.required = 0;
				 }
			 };

			 fflctrl.addFieldFormats =  function(){
				 window.location.href = '#/AddFieldFormats';
			 };

			 fflctrl.reset=  function(){
				 $scope.model.selected = {};
			 };

			 fflctrl.saveFieldFormats=  function(id){


				 // get row to update 
				 var selectedRow  = angular.copy($scope.model.selected);
				 selectedRow.format = fflctrl.formats[$scope.model.selected.format.id].value;
				 $scope.model.selected.format.value = fflctrl.formats[$scope.model.selected.format.id].value;

				 var validationSuccess = true;
				 if(!/^[\w-]+$/.test(selectedRow.alias))
				 {validationSuccess = false;}

				 var isValidColumnOrder= true;
				 angular.forEach($scope.data, function(fieldFormat, iterator){

					 if(isValidColumnOrder)
					 {
						 if(fieldFormat.columnOrder ==  selectedRow.columnOrder)
						 {
							 isValidColumnOrder = false;
						 }
					 }
				 });



				 if(isValidColumnOrder)
				 {
					 if(validationSuccess){
						 // update display
						 $scope.model.fieldFormats[id] = angular.copy($scope.model.selected);

						 if($scope.model.selected.required == 1)
						 {
							 $scope.model.fieldFormats[id].required = true;
						 }
						 else{
							 $scope.model.fieldFormats[id].required = false;
						 }



						 // update db  
						 ApiService.SaveFieldFormat(selectedRow);


						 // refresh display
						 fflctrl.reset();

					 }else
						 Flash.create('danger', "Alias must not contain space..."); 

				 }
				 else
				 {
					 Flash.create('danger', "Another feild with same column order alredy exists.");  
				 }






			 };

		 } ]);



app.controller(
		'AddFieldFormatController',
		[
		 '$scope',
		 '$rootScope',
		 '$routeParams',
		 'Flash',
		 'ApiService',
		 function($scope,$rootScope,$routeParams,Flash ,ApiService) {

			 var affctrl = this;

			 affctrl.formats = [{'id':'0',

				 'value' : 'dd/MM/yyyy'},
			                    {'id':'1',

					 'value' : 'dd-MM-yyyy'},
			                    {'id':'2',

						 'value' : 'hh:mm:ss'},
			                    {'id':'3',

							 'value' : 'string'}];
			 ApiService.GetPartners(function(response){
				 $scope.Partners = response;


			 });


			 $scope.value = "1";
			 affctrl.dataMin="1";
			 affctrl.dataMax="100"
					 affctrl.dataStep="1"
					 affctrl.dataAddclass="someClass"
					 affctrl.dataWidth="130px"

					 affctrl.reset= function(){
				 var partnerid= affctrl.inputData.partnerId;
				 affctrl.inputData = {};

				 affctrl.inputData.partnerId =partnerid;
			 } 




			 //Add Mode
			 affctrl.PartnerChanged = function(){

				 ApiService.GetFileFormatByPartnerId(affctrl.inputData.partnerId, function(response){
					 $scope.data = response; 
				 });
			 }



			 affctrl.saveFieldFormat=  function(){





				 var validationSuccess = true;
				 if(!/^[\w-]+$/.test(affctrl.inputData.alias))
				 {validationSuccess = false;}

				 var isValidColumnOrder= true;
				 angular.forEach($scope.data, function(fieldFormat, iterator){

					 if(fieldFormat.columnOrder ==  $scope.value)
					 {
						 isValidColumnOrder = false;
					 }
				 });

				 if(isValidColumnOrder)
				 {
					 if(validationSuccess){
						 affctrl.inputData.columnOrder = $scope.value;
						 if(affctrl.inputData.required ==null)
						 {
							 affctrl.inputData.required ='false';
						 }
						 affctrl.inputData.format =  affctrl.formats[affctrl.selectedFormat].value; 
						 ApiService.SaveFieldFormat(affctrl.inputData);
					 }else
						 Flash.create('danger', "Alias must not contain space..."); 

				 }
				 else
				 {
					 Flash.create('danger', "Another feild with same column order alredy exists.");  
				 }

			 };



			 affctrl.saveFieldFormatandExit=  function(){
				 affctrl.saveFieldFormat();
				 window.location.href = '#/FlowSelection/OD';
			 };	 

		 } ]);






app.factory(
		'AuthenticationService',
		[
		 'Base64',
		 '$http',
		 '$cookieStore',
		 '$rootScope',
		 '$timeout',
		 'Flash',
		 function(Base64, $http, $cookieStore, $rootScope,
				 $timeout,Flash) {
			 var service = {};

			 service.Login = function(userName, password,
					 callback) {
				 var encodedString = '{"userName":"'
						 + encodeURIComponent(userName)
						 + '","password":"'
						 + encodeURIComponent(password) + '"}';
				 $http(
						 {
							 method : 'POST',
							 url : 'services/v1/partnerService/loginUser',
							 data : encodedString,
							 headers : {
							 'Content-Type' : 'application/json'
						 }
						 })
				 .success(function(response) {
					 callback(response);
				 })
				 .error(
						 function(response) {
							 Flash.create('danger', "Some error occurred while validating User");
						 })
			 };

			 service.SetCredentials = function(userName,
					 password) {
				 var authdata = Base64.encode(userName + ':'
						 + password);

				 $rootScope.globals = {
						 currentUser : {
					 userName : userName,
					 authdata : authdata
				 }
				 };
				 $http.defaults.headers.common['Authorization'] = 'Basic '
						 + authdata;
				 $cookieStore.put('globals', $rootScope.globals);
			 };

			 service.ClearCredentials = function() {
				 $rootScope.globals = {};
				 $cookieStore.remove('globals');
			 };
			 service.SetSession = function(key, value) {
				 $rootScope.key = value;
				 $cookieStore.put(key, $rootScope.key);
			 };
			 return service;
		 } ]);

app.service(
		'Base64',
		function() {
			var keyStr = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=';

			return {
				encode : function(input) {
				var output = "";
				var chr1, chr2, chr3 = "";
				var enc1, enc2, enc3, enc4 = "";
				var i = 0;

				do {
					chr1 = input.charCodeAt(i++);
					chr2 = input.charCodeAt(i++);
					chr3 = input.charCodeAt(i++);

					enc1 = chr1 >> 2;
							 enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
							 enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
							 enc4 = chr3 & 63;

							 if (isNaN(chr2)) {
								 enc3 = enc4 = 64;
							 } else if (isNaN(chr3)) {
								 enc4 = 64;
							 }

							 output = output + keyStr.charAt(enc1)
							 + keyStr.charAt(enc2)
							 + keyStr.charAt(enc3)
							 + keyStr.charAt(enc4);
							 chr1 = chr2 = chr3 = "";
							 enc1 = enc2 = enc3 = enc4 = "";
				} while (i < input.length);

				return output;
			},

				decode : function(input) {
				var output = "";
				var chr1, chr2, chr3 = "";
				var enc1, enc2, enc3, enc4 = "";
				var i = 0;
				var base64test = /[^A-Za-z0-9\+\/\=]/g;
				if (base64test.exec(input)) {
					window
					.alert("There were invalid base64 characters in the input text.\n"
							+ "Valid base64 characters are A-Z, a-z, 0-9, '+', '/',and '='\n"
							+ "Expect errors in decoding.");
				}
				input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

				do {
					enc1 = keyStr.indexOf(input.charAt(i++));
					enc2 = keyStr.indexOf(input.charAt(i++));
					enc3 = keyStr.indexOf(input.charAt(i++));
					enc4 = keyStr.indexOf(input.charAt(i++));

					chr1 = (enc1 << 2) | (enc2 >> 4);
					chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
					chr3 = ((enc3 & 3) << 6) | enc4;

					output = output + String.fromCharCode(chr1);

					if (enc3 != 64) {
						output = output + String.fromCharCode(chr2);
					}
					if (enc4 != 64) {
						output = output + String.fromCharCode(chr3);
					}

					chr1 = chr2 = chr3 = "";
					enc1 = enc2 = enc3 = enc4 = "";

				} while (i < input.length);

				return output;
			}
			};

		});

app.service('ValidationService',['$rootScope', '$http','$cookieStore','Flash',function($rootScope, $http,$cookieStore,Flash){

	var service = this;

	service.IsRegexDuplicate = function(selectedRow, collection)
			{
		var isduplicate= false;
		angular.forEach(collection, function(fieldFormat, iterator){

			if(!isduplicate)
			{
				if(fieldFormat.columnOrder ==  selectedRow.columnOrder)
				{
					isduplicate = true;
				}
			}
			
			

		});
		
		return isduplicate;

			}
}]);

app.service('ApiService',['$rootScope', '$http','$cookieStore','Flash', function($rootScope, $http,$cookieStore,Flash ){


	var service = this;

	service.GetUser = function(userName, callback){

		$http({
			method : 'GET',
			url : 'services/v1/partnerService/getUser/' + userName ,
			headers : {
			'Content-Type' : 'application/json'
		}
		})
		.success(function(response) {
			callback(response);
		})
		.error(
				function(response) {
					Flash.create('danger', "Some error occurred while getting User");
					cfpLoadingBar.complete();
				});

	}


	service.GetUsers = function(callback){
		$http({
			method : 'GET',
			url : 'services/v1/partnerService/getUsers',
			headers : {
			'Content-Type' : 'application/json'
		}
		})
		.success(function(response) {
			callback(response);
		})
		.error(
				function(response) {
					Flash.create('danger', "Some error occurred while listing Users");
				});
	}

	service.GetUsersByPartnerId = function(partnerId, callback){
		$http({
			method : 'GET',
			url : 'services/v1/partnerService/getUserByPartnerId/'+partnerId,
			headers : {
			'Content-Type' : 'application/json'
		}
		})
		.success(function(response) {
			callback(response);
		})
		.error(
				function(response) {
					Flash.create('danger', "Some error occurred while listing Users");
				});
	}


	service.GetPartner = function(partnerId, callback){

		$http({
			method : 'GET',
			url : 'services/v1/partnerService/getPartnerById/' + partnerId,
			headers : {
			'Content-Type' : 'application/json'
		}
		})
		.success(function(response) {
			callback(response);
		})
		.error(
				function(response) {
					Flash.create('danger', "Some Error occured while getting Partner Information");
					cfpLoadingBar.complete();
				});
	}


	service.GetPartners = function(callback){

		$http({
			method : 'GET',
			url : 'services/v1/partnerService/getPartnersInfo',
			headers : {
			'Content-Type' : 'application/json'
		}
		})
		.success(function(response) {
			callback(response);
		})
		.error(
				function(response) {
					Flash.create('danger', "Some Error occured while getting Partner Information");
				});
	}
	service.getTrackingNumberValidationByCarrierCode = function(carrierCode,mask, callback){

		var encodedString = '{"carrierCode":"'
				+ carrierCode
				+ '","mask":"'
				+ mask+'"}';
		var double="\\\\";
		var res = encodedString.split('\\').join(double); 

		$http(
				{
					method : 'POST',
					url : 'services/v1/partnerService/getTrackingNumberValidationUsingCarrierCode',
					data : res,  
					headers : {
					'Content-Type' : 'application/json'
				}
				})
		.success(function(response) {
			callback(response);
		})
		.error(
				function(response) {
					Flash.create('danger', "Some Error occured while getting TrackingNumber validation rules");
				});
	}

	service.getQueueServersByServerName = function(serverName, callback){
		$http({
			method : 'GET',
			url : 'services/v1/partnerService/getQueueServersUsingServerName/'+ serverName,
			headers : {
			'Content-Type' : 'application/json'
		}
		})
		.success(function(response) {
			callback(response);
		})
		.error(
				function(response) {
					Flash.create('danger', "Some Error occured while getting QueueServer");
				});
	}

	service.getCarrierConfigByCode = function(code, callback){
		$http({
			method : 'GET',
			url : 'services/v1/partnerService/getCarrierConfigUsingCode/'+ code,
			headers : {
			'Content-Type' : 'application/json'
		}
		})
		.success(function(response) {
			callback(response);
		})
		.error(
				function(response) {
					Flash.create('danger', "Some Error occured while getting Carrier Configuration");
				});
	}

	service.GetStaticParameters = function(partnerId,userId,carrierId, callback){

		$http({
			method : 'GET',
			url : 'services/v1/partnerService/getStaticParameters/partnerId/'+partnerId+'/userName/'+userId+'/carrierCode/'+carrierId,
			headers : {
			'Content-Type' : 'application/json'
		}
		})
		.success(function(response) {
			callback(response);
		})
		.error(
				function(response) {
					Flash.create('danger', "Some Error occured while getting Static Parameters");
				});
	}

	service.GetCarrierConfig = function(callback){

		$http({
			method : 'GET',
			url : 'services/v1/partnerService/getCarrierConfig' ,
			headers : {
			'Content-Type' : 'application/json'
		}
		})
		.success(function(response) {
			callback(response);
		})
		.error(
				function(response) {
					Flash.create('danger', "Some Error occured while getting carrier configuration");
				});
	}


	service.GetFileFormatByPartnerId = function(partnerId, callback){

		$http({
			method : 'GET',
			url : 'services/v1/partnerService/getFileFormat/'+partnerId ,
			headers : {
			'Content-Type' : 'application/json'
		}
		})
		.success(function(response) {
			callback(response);
		})
		.error(
				function(response) {
					Flash.create('danger', "Some Error occured while getting field formats for selected partner");
				});
	}

	service.SaveFieldFormat= function(fieldFormat){

		$http({
			method : 'POST',
			url : 'services/v1/partnerService/saveFileFormats/',
			data: fieldFormat,
			headers : {
			'Content-Type' : 'application/json'
		}
		})
		.success(function(response) {
			Flash.create('success', "Field format created successfully!");
		})
		.error(
				function(response) {
					Flash.create('danger', "Error creating field format for partner!");
				});
	}


	service.GetAllStaticParameters= function(callback){
		$http({
			method : 'GET',
			url : 'services/v1/partnerService/getStaticParameter',
			headers : {
			'Content-Type' : 'application/json'
		}
		})
		.success(function(response) {
			callback(response);
		})
		.error(
				function(response) {
					Flash.create('danger', "Some error occurred while listing static parameter");
				});

	}


}]);