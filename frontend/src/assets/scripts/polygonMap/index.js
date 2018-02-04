import * as $ from 'jquery';
import 'leaflet.markercluster';

export default (function () {
  if ($('#mapid').length > 0) {
    $.getJSON("http://178.62.30.127/cid", function (data) {

      console.log(data);
      var mymap = L.map('mapid').setView([41.544113, 2.434941], 14);
      var polygons = [];

      var type_counter = {"Comercial": 0, "Turisme": 0, "Mobilitat": 0};
      var ocupacio_counter = {"Lliure": 0, "Ocupat": 0, "Ple": 0};
      for (var key in data) {
        // var marker = L.marker([51.5, -0.09]).addTo(mymap);

        type_counter["Comercial"] = type_counter["Comercial"] +  data[key].nplacesocupadesperveh[0];
        type_counter["Turisme"] = type_counter["Turisme"] +  data[key].nplacesocupadesperveh[1];
        type_counter["Mobilitat"] = type_counter["Mobilitat"] +  data[key].nplacesocupadesperveh[2];

        var ocupacio_label = "Ple";
        var color = 'red';
        var ocupacio = data[key].nplacesocupades/data[key].nplaces;
        if(ocupacio < 1. && ocupacio >= 0.5){
          ocupacio_label = "Ocupat";
          color = 'orange';
        } else if(ocupacio < 0.5){
          ocupacio_label = "Lliure";
          color = 'green';
        }

        ocupacio_counter[ocupacio_label] = ocupacio_counter[ocupacio_label] + 1;

        var polygon = data[key].WKT.replace("POLYGON ((", "").replace("))", "");
        polygon = polygon.split(", ").map(function(p){return p.split(" ").reverse();});
        polygons[key] = L.polygon(polygon, {color: color}).addTo(mymap);

        polygons[key].bindPopup("Zona <b>"+data[key].ID+"</b>" +
          "<br>Mida <b>"+Math.round(Number(data[key].mida)*100)/100+"m</b>" +
          "<br>Ocupació <b>"+data[key].nplacesocupades+"/"+data[key].nplaces+"</b>");
      }

      console.log(type_counter);
      console.log(ocupacio_counter);

      var total_vehicles = type_counter["Mobilitat"] + type_counter["Turisme"] + type_counter["Comercial"];
      $('#progress-bar-mobilitat').css('width', type_counter["Mobilitat"]/total_vehicles*100 + "%");
      $('#span-mobilitat').text(Math.round(type_counter["Mobilitat"]/total_vehicles*100) + "%");
      $('#small-mobilitat').text(type_counter["Mobilitat"] + " Vehicles");

      $('#progress-bar-turista').css('width', type_counter["Turisme"]/total_vehicles*100 + "%");
      $('#span-turista').text(Math.round(type_counter["Turisme"]/total_vehicles*100) + "%");
      $('#small-turista').text(type_counter["Turisme"] + " Vehicles");

      $('#progress-bar-comercial').css('width', type_counter["Comercial"]/total_vehicles*100 + "%");
      $('#span-comercial').text(Math.round(type_counter["Comercial"]/total_vehicles*100) + "%");
      $('#small-comercial').text(type_counter["Comercial"] + " Vehicles");


      var ocupacio_total = ocupacio_counter["Lliure"] + ocupacio_counter["Ocupat"] + ocupacio_counter["Ple"];
      $('#pie-empty').data('easyPieChart').update(ocupacio_counter["Lliure"]/ocupacio_total*100);
      $('#pie-occuped').data('easyPieChart').update(ocupacio_counter["Ocupat"]/ocupacio_total*100);
      $('#pie-full').data('easyPieChart').update(ocupacio_counter["Ple"]/ocupacio_total*100);

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

