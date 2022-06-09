package com.example.lab1_simran_c0834481_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {




    private Marker locationMarker;
    private List<Marker> markers = new ArrayList<>();
    private MarkerOptions options;
    PolylineOptions polylineOptions = new PolylineOptions();

    private List<Polygon> listOfPolyGone = new ArrayList<>();
    private Polyline polyline;
    private Polygon polygon1;
    int locationCount = 0;
    private int strokeColorCLick = 0;
    private static final int COLOR_BLACK_ARGB = 0xff000000;
    private SupportMapFragment supportMapFragment;
    private GoogleMap map;
    private int loginForPolygon;


    private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem DOT = new Dot();
    List<Polyline> list = new ArrayList<>();
    private static final int COLOR_WHITE_ARGB = 0xfffff23f;
    private static final int COLOR_DARK_GREEN_ARGB = 0xff388E3C;
    private static final int COLOR_LIGHT_GREEN_ARGB = 0xff81C444;
    private static final int COLOR_DARK_ORANGE_ARGB = 0xffF57F17;
    private static final int COLOR_LIGHT_ORANGE_ARGB = 0xffF9A825;
    List<LatLng> latLngs = new ArrayList<>();
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    private static final int POLYGON_STROKE_WIDTH_PX = 8;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
//        createMap();




        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getBaseContext());

        // Showing status
        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        } else {
            //            FragmentManager fm = getSupportFragmentManager();
//            supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map_v2);
//            if (supportMapFragment == null) {
//                supportMapFragment = new SupportMapFragment();
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.add(R.id.map_v2, supportMapFragment);
//                ft.commit();
//                fm.executePendingTransactions();
//            }
//
//            if (supportMapFragment != null) {
//                supportMapFragment.getMapAsync(MainActivity.this);
//            }
            
            // Google Play Services are available

            FragmentManager fm = getSupportFragmentManager();
            supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map_v2);
            if (supportMapFragment == null) {
                supportMapFragment = new SupportMapFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.map_v2, supportMapFragment);
                ft.commit();
                fm.executePendingTransactions();
            }

            if (supportMapFragment != null) {
                supportMapFragment.getMapAsync(MainActivity.this);
            }



        }



        //Adding Views

        findViewById(R.id.solid_color).setOnClickListener(v -> {
            loginForPolygon++;
            solidColorVariations();

        });
        findViewById(R.id.stroke_color).setOnClickListener(v -> {
            strokeColorCLick++;

changeStrokeColor();
        });


    }

    private void changeStrokeColor(){


        if (strokeColorCLick == 1) {


            polyline.setColor(Color.GREEN);
            polygon1.setStrokeColor(Color.GREEN);
        } else if (strokeColorCLick == 2) {


            polyline.setColor(Color.BLACK);
            polygon1.setStrokeColor(Color.BLACK);
        } else if (strokeColorCLick == 3) {
            polygon1.setStrokeColor(Color.BLUE);
            polyline.setColor(Color.BLUE);

        } else if (strokeColorCLick == 4) {
            strokeColorCLick = 0;
            polygon1.setStrokeColor(Color.WHITE);
            polyline.setColor(Color.WHITE);

        }

    }
private void solidColorVariations(){
    if (loginForPolygon == 1) {
        stylePolygon(COLOR_DARK_ORANGE_ARGB);
    } else if (loginForPolygon == 2) {
        stylePolygon(COLOR_LIGHT_ORANGE_ARGB);
    } else if (loginForPolygon == 3) {
        stylePolygon(COLOR_LIGHT_GREEN_ARGB);
    } else if (loginForPolygon == 4) {
        loginForPolygon = 0;
        stylePolygon(COLOR_BLACK_ARGB);
    }

}
    private void drawMarker(LatLng point) {
        if (polylineOptions == null){
            polylineOptions = new PolylineOptions();
        }
        polylineOptions.add(point);

        options = new MarkerOptions();

        // Setting latitude and longitude for the marker
        options.position(point);

        // Adding marker on the Google Map
        locationMarker = map.addMarker(options);
        locationMarker.setTitle("A");
        locationMarker.showInfoWindow();
        markers.add(locationMarker);

        polyline = this.map.addPolyline(polylineOptions);
        polyline.setClickable(true);
        polyline.setColor(Color.RED);
        list.add(polyline);
        map.setOnPolylineClickListener(line -> {
            line.remove();
//            if (polyLineClick == 1){
//                polyLineClick = 0;
//                list.remove(line);
//                map.clear();
//            } else {
//                polyLineClick++;
//            }

        });

//        map.setOnPolygonClickListener(polygon -> {
//            polygon.remove();
//            map.clear();
//            map.resetMinMaxZoomPreference();
//            locationCount = 0;
//            polylineOptions = null;
//        });

        map.setOnPolygonClickListener(polygon -> {
            polygon.remove();
            map.clear();
            map.resetMinMaxZoomPreference();
            locationCount = 0;
            polylineOptions = null;
        });

    }

    private void stylePolygon(int color) {
        if (list.size() <= 4) {
            polygon1 = map.addPolygon(new PolygonOptions()
                    .clickable(true)
                    .add(
                            list.get(0).getPoints().get(0),
                            list.get(1).getPoints().get(1),
                            list.get(2).getPoints().get(2),
                            list.get(3).getPoints().get(3)));

            polygon1.setTag("alpha");
            String type = "";
            // Get the data object stored with the polygon.
            if (polygon1.getTag() != null) {
                type = polygon1.getTag().toString();
            }
            List<PatternItem> pattern = null;
            int strokeColor = COLOR_DARK_ORANGE_ARGB;
            int fillColor = color;

            polygon1.setStrokePattern(pattern);
            polygon1.setStrokeColor(strokeColor);
            polygon1.setFillColor(fillColor);
            listOfPolyGone.add(polygon1);
            polygon1.setStrokeWidth(POLYGON_STROKE_WIDTH_PX);

        }


    }


    @Override
    public void onMapReady(GoogleMap gMap) {
        map = gMap;
        CameraUpdate point = CameraUpdateFactory.newLatLng(new LatLng(43.6532, 79.3832));

// moves camera to coordinates
        map.moveCamera(point);
// animates camera to coordinates
        map.animateCamera(point);
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            public void onMapClick(LatLng point) {
                if (locationCount < 4) {
                    locationCount++;
                    latLngs.add(point);
                    drawMarker(point);
                } else {
                    Toast.makeText(getBaseContext(), "You can add up to 4 locations only", Toast.LENGTH_SHORT).show();
                }
                double totalDistance = 0.0;
                if (locationCount == 1) {

                } else if (locationCount == 2) {
                    totalDistance = CalculationByDistance(latLngs.get(0), latLngs.get(1));
                    Toast.makeText(mContext, String.format("%.0f", totalDistance) + " KM", Toast.LENGTH_SHORT).show();
                } else if (locationCount == 3) {
                    totalDistance = CalculationByDistance(latLngs.get(0), latLngs.get(1)) +
                            CalculationByDistance(latLngs.get(1), latLngs.get(2));
                } else if (locationCount == 4) {
                    totalDistance = CalculationByDistance(latLngs.get(0), latLngs.get(1)) +
                            CalculationByDistance(latLngs.get(1), latLngs.get(2)) + CalculationByDistance(latLngs.get(2), latLngs.get(3)) +
                            CalculationByDistance(latLngs.get(3), latLngs.get(0));
                    Log.d("LOGGER", totalDistance + "");
                    stylePolygon(COLOR_LIGHT_GREEN_ARGB);
                    polylineOptions.color(Color.GREEN);
//
                }
                Toast.makeText(mContext, String.format("%.0f", totalDistance) + " KM", Toast.LENGTH_SHORT).show();

            }

        });

        map.setOnMarkerClickListener(marker -> {
            marker.remove();
            locationCount--;
            return false;
        });
    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {


//
//        double dLat = Math.toRadians(lat2 - lat1);
//        double dLon = Math.toRadians(lon2 - lon1);
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
//                + Math.cos(Math.toRadians(lat1))
//                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
//                * Math.sin(dLon / 2);
//        double c = 2 * Math.asin(Math.sqrt(a));
//        double valueResult = Radius * c;
//        double km = valueResult / 1;


        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return km;
    }
}