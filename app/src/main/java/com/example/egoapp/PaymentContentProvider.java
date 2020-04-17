package com.example.egoapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.example.egoapp.DBHandler.PaymentDB;
import com.example.egoapp.Object.Payment;

public class PaymentContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.example.egoapp.paymentcontentprovider";

    public static final int NO_MATCH = -1;
    public static final int ALL_PAYMENTS_URI = 0;
    public static final int SINGLE_PAYMENT_URI = 1;

    private PaymentDB db = null;
    private UriMatcher uriMatcher = null;

    public PaymentContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleteCount = 0;
        switch (uriMatcher.match(uri)) {
            case SINGLE_PAYMENT_URI:
                String paymentId = uri.getLastPathSegment();
                deleteCount = db.deletePayment(Long.getLong(paymentId));
                getContext().getContentResolver().notifyChange(uri, null);
                return deleteCount;
            case ALL_PAYMENTS_URI:
                deleteCount = db.deletePayment(selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return deleteCount;
            default:
                throw new UnsupportedOperationException("URI " + uri + " is not supported");
        }
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch(uriMatcher.match(uri)) {
            case ALL_PAYMENTS_URI:
                return "vnd.android.cursor.dir/vnd.egoapp.payment.payment";
            case SINGLE_PAYMENT_URI:
                return "vnd.android.cursor.item/vnd.egoapp.payment.payment";
            default:
                throw new UnsupportedOperationException("URI " + uri + " is not supported");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        switch (uriMatcher.match(uri)) {
            case ALL_PAYMENTS_URI:
                long insertId = db.insertPayment(new Payment(values));
                getContext().getContentResolver().notifyChange(uri, null);
                return uri.buildUpon().appendPath(
                        String.valueOf(insertId)).build();
            default:
                throw new UnsupportedOperationException("URI " + uri + " is not supported");
        }
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        db = new PaymentDB(getContext());
        uriMatcher = new UriMatcher(NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "payment", ALL_PAYMENTS_URI);
        uriMatcher.addURI(AUTHORITY, "payment/#", SINGLE_PAYMENT_URI);
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        switch (uriMatcher.match(uri)) {
            case ALL_PAYMENTS_URI:
                return db.queryPayments(projection, selection,
                        selectionArgs, sortOrder);
            default:
                throw new UnsupportedOperationException("URI " + uri + " is not supported");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        int updateCount = 0;
        switch (uriMatcher.match(uri)) {
            case SINGLE_PAYMENT_URI:
                String paymentId = uri.getLastPathSegment();
                Payment payment = new Payment(values);
                payment.setPayID(Integer.getInteger(paymentId));
                updateCount = db.updatePayment(payment);
                getContext().getContentResolver().notifyChange(uri, null);
                return updateCount;
            case ALL_PAYMENTS_URI:
                updateCount = db.updatePayment(values, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return updateCount;
            default:
                throw new UnsupportedOperationException("URI " + uri + " is not supported");
        }
    }
}
