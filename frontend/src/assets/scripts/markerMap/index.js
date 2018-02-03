import * as $ from 'jquery';

export default (function () {
  if ($('#mapid').length > 0) {
    $.getJSON("http://178.62.30.127/cid", function (data) {

      console.log(data);
      var mymap = L.map('mapid').setView([41.538113, 2.444741], 13);
      var polygons = [];
      //var marker = [];
      for (var key in data) {

        //var lat = data[key].LAT;
        //var lng = data[key].LNG;
        //lat = lat.replace(",", ".");
        //lng = lng.replace(",", ".");

        //marker[key] = L.marker([lat, lng]).addTo(mymap);

        var polygon = data[key].WKT.replace("POLYGON ((", "").replace("))", "");
        polygon = polygon.split(", ").map(function(p){return p.split(" ").reverse();});
        polygons[key] = L.polygon(polygon).addTo(mymap);
      }
      L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
        maxZoom: 18,
        attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
        '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
        'Imagery © <a href="http://mapbox.com">Mapbox</a>',
        id: 'mapbox.streets'
      }).addTo(mymap);
    });
  }
}())
