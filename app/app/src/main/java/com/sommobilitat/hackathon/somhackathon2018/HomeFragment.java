package com.sommobilitat.hackathon.somhackathon2018;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sommobilitat.hackathon.somhackathon2018.controllers.APIController;
import com.sommobilitat.hackathon.somhackathon2018.controllers.VolleyController;
import com.sommobilitat.hackathon.somhackathon2018.models.LatLng;
import com.sommobilitat.hackathon.somhackathon2018.models.Zones;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.infowindow.BasicInfoWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private View rootView;
    private TextView zoomInView;
    private TextView zoomOutView;
    private MapView osmMap;

    ArrayList<Zones> zones;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));
    }

    @Override
    public void onResume() {
        super.onResume();

        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        setUpElements();
        setUpListeners();

        osmMap.setMultiTouchControls(true);
        osmMap.setTileSource(TileSourceFactory.MAPNIK);

        //Geopoint located in Mataro
        centerMap(new GeoPoint(41.538035, 2.438375));

        getZones();
        return rootView;
    }

    private void centerMap(GeoPoint geoPoint) {
        IMapController mapController = osmMap.getController();
        mapController.setZoom(15);
        mapController.setCenter(geoPoint);
    }

    public void getZones() {
        zones = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIController.getURLForZones(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, response.toString());
                        for (int i = 1; i <= response.length(); ++i) {
                            JSONObject zonaObjectJson;
                            try {
                                zonaObjectJson = response.getJSONObject("" + i);

                                int id = zonaObjectJson.getInt(APIController.ENDPOINT_CID_ID);
                                String name = zonaObjectJson.getString(APIController.ENDPOINT_CID_ZONA);
                                double area = zonaObjectJson.getDouble(APIController.ENDPOINT_CID_AREA);
                                double perimeter = zonaObjectJson.getDouble(APIController.ENDPOINT_CID_PERIMETRE);
                                double width = zonaObjectJson.getDouble(APIController.ENDPOINT_CID_AMPLADA);
                                double height = zonaObjectJson.getDouble(APIController.ENDPOINT_CID_ALCADA);
                                int nPlaces = zonaObjectJson.getInt(APIController.ENDPOINT_CID_PLACES);
                                int nPlacesOcuppied = zonaObjectJson.getInt(APIController.ENDPOINT_CID_PLACES_TOTAL);
                                LatLng center = new LatLng(
                                        zonaObjectJson.getDouble(APIController.ENDPOINT_CID_LAT),
                                        zonaObjectJson.getDouble(APIController.ENDPOINT_CID_LNG));
                                ArrayList<LatLng> wkt = Zones.polygonToWKTArray(
                                        zonaObjectJson.getString(APIController.ENDPOINT_CID_WKT));

                                zones.add(new Zones(id, name, area, perimeter, width, height, nPlaces, nPlacesOcuppied, center, wkt));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (zones != null) {
                            for (int i = 0; i < zones.size(); ++i) {
                                Polygon polygon = new Polygon();
                                polygon.setTitle("Zona: " + zones.get(i).getName() + ", ID: " + zones.get(i).getId());
                                int placesLliures = zones.get(i).getnPlacesTotals() - zones.get(i).getnPlacesOcuppied();
                                polygon.setSubDescription("Lliures: " + placesLliures);
                                polygon.setVisible(true);
                                if (placesLliures / zones.get(i).getnPlacesTotals() < 0.2) {
                                    polygon.setStrokeColor(Color.parseColor("#f44336"));
                                } else if (placesLliures / zones.get(i).getnPlacesTotals() < 0.5) {
                                    polygon.setStrokeColor(Color.parseColor("#ff9800"));
                                } else {
                                    polygon.setStrokeColor(Color.parseColor("#4caf50"));
                                }
                                polygon.setStrokeWidth(15);
                                polygon.setInfoWindow(new BasicInfoWindow(R.layout.bonuspack_bubble, osmMap));
                                List<GeoPoint> geoPoints = new ArrayList<>();
                                for (int j = 0; j < zones.get(i).getWkt().size(); ++j) {
                                    geoPoints.add(
                                            new GeoPoint(zones.get(i).getWkt().get(j).getLat(),
                                                    zones.get(i).getWkt().get(j).getLng()));
                                }
                                polygon.setPoints(geoPoints);

                                osmMap.getOverlayManager().add(polygon);
                            }
                            osmMap.invalidate();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "That didn't work!");
                    }
                });
        VolleyController.getInstance(getContext()).addToQueue(jsonObjectRequest);
    }

    private void setUpElements() {
        osmMap = (MapView) rootView.findViewById(R.id.osmap);
        zoomInView = (TextView) rootView.findViewById(R.id.home_zoom_in);
        zoomOutView = (TextView) rootView.findViewById(R.id.home_zoom_out);
    }

    private void setUpListeners() {
        zoomInView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (osmMap != null && osmMap.canZoomIn()) {
                    osmMap.getController().setZoom(osmMap.getZoomLevel() + 1);
                }
            }
        });

        zoomOutView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (osmMap != null && osmMap.canZoomOut()) {
                    osmMap.getController().setZoom(osmMap.getZoomLevel() - 1);
                }
            }
        });
    }
}
