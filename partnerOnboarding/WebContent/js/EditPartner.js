angular.module('LoginModule')
.run(['$http','$cookieStore', function ($http,$cookieStore) {
    $http.defaults.headers.common['Authorization'] =  'Basic '+$cookieStore.get('globals').currentUser.authdata;
    $http.defaults.headers.common['Accept'] = 'application/json;odata=verbose';
}])

.directive('numbersOnly', function(){
   return {
     require: 'ngModel',
     link: function(scope, element, attrs, modelCtrl) {
    	 modelCtrl.$parsers.push(function (inputValue) {
           if (inputValue == undefined) return '' 
           var transformedInput = inputValue.replace(/[^0-9]/g, ''); 
           if (transformedInput!=inputValue) {
              modelCtrl.$setViewValue(transformedInput);
              modelCtrl.$render();
           }         
           return transformedInput;         
       });
     }
   };
})
.controller('PartnerInfoController', ['$scope', '$http' ,'AuthenticationService', function($scope, $http,AuthenticationService) {
	
	this.postForm = function() {
		$scope.partnerCode = this.inputData.name;
		var encodedString = '{"countryCode":"' +encodeURIComponent(this.inputData.countryCode)+
		'","name":"' +
		encodeURIComponent(this.inputData.name) +
		'","fileExt":"' +
		encodeURIComponent(this.inputData.fileExt)+
		'","fileNamePattern":"' +
		encodeURIComponent(this.inputData.fileNamePattern)+
		'","delimeter":"' +
		encodeURIComponent(this.inputData.delimeter)+
		'","header":"' +
		encodeURIComponent(this.inputData.header)+
		'","blankFile":"' +
		encodeURIComponent(this.inputData.blankFile)+
		'","maxSize":"' +
		encodeURIComponent(this.inputData.maxSize)+
		'","frequency":"' +
		encodeURIComponent(this.inputData.frequency)+
		'","services":["' +
		encodeURIComponent(this.inputData.services)+
		'"]}';
		/*alert(encodedString);*/
		$http({
			method: 'POST',
			url: '../services/v1/partnerService/savePartnerInfo',
			data: encodedString,
			headers: {'Content-Type': 'application/json'}
		})
		.success(function(data, status, headers, config,response) {
			alert(status);
			/*console.log(response);*/
			if (status == 200) {
				AuthenticationService.SetSession('partnerCode',$scope.partnerCode);
				window.location = "http://localhost:8123/partnerOnboarding/html/PartnerInfo2.html";						
			}
		})
		.error(function(data, status, headers, config) {
			$scope.errorMsg = 'Unable to submit form';
		});
	}

}]);
