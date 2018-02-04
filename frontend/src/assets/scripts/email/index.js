import * as $ from 'jquery';

export default (function () {
  $.getJSON("http://178.62.30.127/parkings/recent", function (data) {

    console.log(data);

    $.each(data, function (key, value) {

      let tipus;
      if (value.tipusVehicle === 0) tipus = 'Comercial';
      if (value.tipusVehicle === 1) tipus = 'Turisme';
      if (value.tipusVehicle === 2) tipus = 'Movilitat reduida';

      $('#recentParkings > tbody:last-child')
        .append('<tr><th scope="row">'+value.id+'</th><td>'+value.parkingArea+'</td><td>'+value.startTime.split(".")[0]+'</td><td>'+tipus+'</td></tr>');
    });

  });
}())
