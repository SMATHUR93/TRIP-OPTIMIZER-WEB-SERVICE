var app = angular.module('myApp', ['gm']);


app.directive('errorMessage', function() {
    return {
        restrict : "E",
        templateUrl : "app/errorMessage/errorMessage.html"
    };
});
app.directive('placesSearchCombo', function() {
    return {
        restrict : "E",
        templateUrl : "app/placesSearchCombo/placesSearchCombo.html"
    };
});
app.directive('sourceAndDestinationCombos', function() {
    return {
        restrict : "E",
        templateUrl : "app/sourceAndDestinationCombos/sourceAndDestinationCombos.html"
    };
});
app.directive('locationCardsCarousalsContainer', function() {
    return {
        restrict : "E",
        templateUrl : "app/locationCardsCarousalsContainer/locationCardsCarousalsContainer.html"
    };
});
app.directive('outputOptimizationMap', function() {
    return {
        restrict : "E",
        templateUrl : "app/outputOptimizationMap/outputOptimizationMap.html"
    };
});
app.directive('outputPathTiles', function() {
    return {
        restrict : "E",
        templateUrl : "app/outputPathTiles/outputPathTiles.html"
    };
});

app.service('Map', function($q) {
    
    this.init = function() {
        var options = {
            center: new google.maps.LatLng(40.7127837, -74.00594130000002),
            zoom: 13,
            disableDefaultUI: true    
        }
        this.map = new google.maps.Map(
            document.getElementById("bigMap"), options
        );
        this.places = new google.maps.places.PlacesService(this.map);
    }
    
    this.search = function(str) {
        var d = $q.defer();
        this.places.textSearch({query: str}, function(results, status) {
            if (status == 'OK') {
                d.resolve(results[0]);
            }
            else d.reject(status);
        });
        return d.promise;
    }
    
    this.addMarker = function(res) {
        if(this.marker) this.marker.setMap(null);
        this.marker = new google.maps.Marker({
            map: this.map,
            position: res.geometry.location,
            animation: google.maps.Animation.DROP
        });
        this.map.setCenter(res.geometry.location);
    }
    
});

app.controller('tripOptimizerController', function($scope, $http) {
    
    $scope.isPlaceSearched=false;
    $scope.arePlacesChecked=false;
    $scope.apiError = false;
    $scope.placesData = {};
    $scope.placesData.allplaces = [];

    $scope.selection = [];
    $scope.orderedPlacesSlection = [];

    // Toggle selection for a given fruit by name
    $scope.toggleSelection = function toggleSelection(place_id) {
        var idx = $scope.selection.indexOf(place_id);
        // Is currently selected
        if (idx > -1) {
            $scope.selection.splice(idx, 1);
        }
        // Is newly selected
        else {
            $scope.selection.push(place_id);
        }
        console.log($scope.selection);
    };

   $scope.search = function() {

        var loc = (!$scope.searchPlace.gm_accessors_)?($scope.searchPlace):(($scope.searchPlace.gm_accessors_.place)?($scope.searchPlace.gm_accessors_.place.jd.formattedPrediction):(""));
        var uri = "http://localhost:8080/api/getplaces?place="+loc;
        var resUrl = encodeURI(uri);
        $http({
            method : "GET",
            url : resUrl
        }).then(function mySuccess(response) {
            //$scope.myWelcome = response.data;
            var serverData = response.data.resultMap;
            Object.getOwnPropertyNames(serverData).forEach(
              function (val, idx, array) {
                $scope.placesData[val] = serverData[val];
                console.log(val + ' -> ' + serverData[val]);
              }
            );
            for(var idx in $scope.placesData.ALL){
                $scope.placesData.allplaces.push($scope.placesData.ALL[idx]);
            }
            //$scope.places = [];
            $scope.apiError = false;
            $scope.isPlaceSearched = true;
        }, function myError(response) {
            $scope.apiError = true;
        });

    };


    $scope.getOptimalTrip = function(){

        var selectionArray = $scope.selection;
        var allPlaces = $scope.placesData.allplaces;
        var jsonData = [];

        for(var idx in allPlaces){
            if(selectionArray.indexOf(allPlaces[idx].place_id)!=-1){
                jsonData.push({
                    "sequence_no":0,
                    "geo_location": {
                        "lat": allPlaces[idx].geometry.location.lat,
                        "lng": allPlaces[idx].geometry.location.lng
                    },
                    "place_id":allPlaces[idx].place_id
                });
            }
        }

        //var loc = (!$scope.searchPlace.gm_accessors_)?($scope.searchPlace):(($scope.searchPlace.gm_accessors_.place)?($scope.searchPlace.gm_accessors_.place.jd.formattedPrediction):(""));
        //var uri = "http://localhost:8080/api/getplaces?place="+loc;
        var resUrl = encodeURI("http://localhost:8080/api/getoptimalroute?source="+jsonData[0].place_id);

        $http({
            method : "POST",
            url : resUrl,
            data: jsonData
        }).then(function mySuccess(response) {
            //$scope.myWelcome = response.data;
            var responseOrderedMapItems = response.data;
            var orderingAtUI = [];
            for(var idx in responseOrderedMapItems){
                for(var idy in allPlaces){
                    if(allPlaces[idy].place_id===responseOrderedMapItems[idx].place_id){
                        orderingAtUI.push(new google.maps.LatLng( allPlaces[idy].geometry.location.lat, allPlaces[idy].geometry.location.lng) );
                        $scope.orderedPlacesSlection.push({
                            "sequence_no":responseOrderedMapItems[idx].sequence_no,
                            "geo_location": {
                                "lat": allPlaces[idy].geometry.location.lat,
                                "lng": allPlaces[idy].geometry.location.lng
                            },
                            'name': allPlaces[idy].name,
                            "place_id":allPlaces[idy].place_id
                        });
                        break;
                    }
                }
            }
            console.log(orderingAtUI);
            var mapCanvas = document.getElementById("optimizationMap");
            var mapOptions = {center: orderingAtUI[0], zoom: 14};
            var map = new google.maps.Map(mapCanvas,mapOptions);

            var flightPath = new google.maps.Polyline({
                path: orderingAtUI,
                strokeColor: "#0000FF",
                strokeOpacity: 0.8,
                strokeWeight: 2
            });
            flightPath.setMap(map);

            $scope.apiError = false;
            $scope.isPlaceSearched = false;
            $scope.arePlacesChecked = true;
        }, function myError(response) {
            $scope.apiError = true;
        });

    };

    /*$scope.send = function() {
        alert($scope.place.name + ' : ' + $scope.place.lat + ', ' + $scope.place.lng);    
    }*/

    //Map.init();
});

/*app.controller('newPlaceCtrl', function($scope, Map) {
    
    $scope.place = {};
    
    $scope.search = function() {
        $scope.apiError = false;
        Map.search($scope.searchPlace)
        .then(
            function(res) { // success
                Map.addMarker(res);
                $scope.place.name = res.name;
                $scope.place.lat = res.geometry.location.lat();
                $scope.place.lng = res.geometry.location.lng();
            },
            function(status) { // error
                $scope.apiError = true;
                $scope.apiStatus = status;
            }
        );
    }
    
    $scope.send = function() {
        alert($scope.place.name + ' : ' + $scope.place.lat + ', ' + $scope.place.lng);    
    }

    //Map.init();
});*/

/*
function myMap() {
  var stavanger = new google.maps.LatLng(58.983991,5.734863);
  var amsterdam = new google.maps.LatLng(52.395715,4.888916);
  var london = new google.maps.LatLng(51.508742,-0.120850);

  var mapCanvas = document.getElementById("optimizationMap");
  var mapOptions = {center: amsterdam, zoom: 4};
  var map = new google.maps.Map(mapCanvas,mapOptions);

  var flightPath = new google.maps.Polyline({
    path: [stavanger, amsterdam, london],
    strokeColor: "#0000FF",
    strokeOpacity: 0.8,
    strokeWeight: 2
  });
  flightPath.setMap(map);
}*/
