package com.gsaves.media3.gsaves.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

//import info.androidhive.barcode.BarcodeReader;
public class BarcodescanningActivity extends AppCompatActivity{

}
/*
public class BarcodescanningActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {
    BarcodeReader barcodeReader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcodescanning);
        // get the barcode reader instance

        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);


    }

    @Override
    public void onScanned(Barcode barcode) {
// playing barcode reader beep sound
        barcodeReader.playBeep();
        // ticket details activity by passing barcode
        Intent intent = new Intent(BarcodescanningActivity.this, BarcodescanResultActivity.class);
        intent.putExtra("code", barcode.displayValue);
        startActivity(intent);
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String s) {
        Toast.makeText(getApplicationContext(), "Error occurred while scanning " + s, Toast.LENGTH_SHORT).show();
    }




    @Override
    public void onCameraPermissionDenied() {
        finish();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
*/
