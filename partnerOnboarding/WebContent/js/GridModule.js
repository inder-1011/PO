var GridModule = angular.module('GridModule', []);



GridModule.directive('grid', function() {

  return {
    restrict: 'E',
    template: '<div></div>',
    scope: true,
    controller: 'gridController',
    link: function(scope, element, attrs, ctrl) {
      scope.$watch(attrs.source, function() {
        if (!ctrl.isSourceResolved()) {
          return;
        }
        ctrl.bindGrid();
      }, true);
    }
  }
  
});


/*

  below is a demonstration of binding and rebinding the 3rd party control.
  It takes into account promises 

*/
GridModule.controller('gridController', function($scope, $attrs, $element) {
  
  var source = $scope.$eval($attrs.source);
  var columns = $scope.$eval($attrs.columns);
  var grid;
  
  var getItemIndex = function(item) {
    var index = -1;
    var primaryKey = grid.dataSource.options.schema.model.id;
    
    for (var i = 0; i < source.length; i++) {
      if (source[i][primaryKey] === item[primaryKey]) {
        index = i;
        break;
      }
    };
    return index;
  };
  
  var getConfig = function() {
    return {
      dataSource: {
          data: source,
          pageSize: 10,
          batch: true,
          schema: {
              model: {
                  id: "Id",
                  fields: {
                      FirstName: { validation: { required: true } },
                      LastName: { validation: { required: true } }
                  }
              }
          }
      },
      pageable: true,
      columns: columns,
      editable: true,
      save: function(e) { //two way data binding 
        var itemIndex = getItemIndex(e.model);
        
        $scope.$apply(function() {
          for (var p in e.values) {
            source[itemIndex][p] = e.values[p];
          }
        });
      }
    };
  };

  this.isSourceResolved = function() {
    return source !== undefined;
  };
  
  this.bindGrid = function() {
    if (!grid) {
      grid = $($element).kendoGrid(getConfig()).data("kendoGrid");
    }
    else { //rebind the grid by updating the data source
      grid.dataSource.data(source);
    }
  };
  
  $scope.$watch($attrs.source, function(data) {
    source = data;
  });
  
  $scope.$on('$destroy', function() {
    grid.destroy();
  });

  
});