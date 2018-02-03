import * as $ from 'jquery';

export default (function () {
  if ($('#mapid').length > 0) {
    $.getJSON("http://178.62.30.127/cid", function (data) {

      console.log(data);

      var polygons = [];
      var marker = [];

      for (var key in data) {

        var lat = data[key].LAT;
        var lng = data[key].LNG;

        var polygon = data[key].WKT.replace("POLYGON ((", "").replace("))", "");
        polygon = polygon.split(", ").map(function(p){return p.split(" ").reverse();});

        marker[key] = L.marker([lat, lng]);
        polygons[key] = L.polygon(polygon);

        marker[key].bindPopup("Zona <b>"+data[key].ID+"</b>" +
          "<br>Alçada <b>"+data[key].ALCADA+"</b>" +
          "<br>Amplada <b>"+data[key].AMPLADA+"</b>" +
          "<br>Area <b>"+data[key].AREA+"</b>");
        polygons[key].bindPopup("Zona <b>"+data[key].ID+"</b>" +
                                "<br>Alçada <b>"+data[key].ALCADA+"</b>" +
                                "<br>Amplada <b>"+data[key].AMPLADA+"</b>" +
                                "<br>Area <b>"+data[key].AREA+"</b>");
      }

      var polygonsLayer = L.layerGroup(polygons);
      var markersLayer = L.layerGroup(marker)

      var city = L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
        maxZoom: 18,
        attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
        '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
        'Imagery © <a href="http://mapbox.com">Mapbox</a>',
        id: 'mapbox.streets'
      });
      var mymap = L.map('mapid', {
        center: [41.538113, 2.444741],
        zoom: 14,
        layers: [city, markersLayer]
        }
      );
      var baseMaps = {
        "Ciutat": city
      }
      var overlayMaps = {
        "Marcadors": markersLayer,
        "Polígons": polygonsLayer
      }
      L.control.layers(baseMaps, overlayMaps).addTo(mymap);
    });
  }
}())
