package com.openclassrooms.realestatemanager.fragment.list.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import com.openclassrooms.realestatemanager.fragment.list.ApartmentDao;
import com.openclassrooms.realestatemanager.fragment.list.ApartmentDatabase;
import com.openclassrooms.realestatemanager.fragment.list.Appartment;
import com.openclassrooms.realestatemanager.fragment.list.Filter;
import com.openclassrooms.realestatemanager.fragment.list.FilterDao;
import com.openclassrooms.realestatemanager.fragment.list.Image;
import com.openclassrooms.realestatemanager.fragment.list.ImageDao;
import com.openclassrooms.realestatemanager.fragment.list.contentprovider.ApartmentProviderContract;

import static com.openclassrooms.realestatemanager.fragment.list.contentprovider.ApartmentProviderContract.AUTHORITY;
import static com.openclassrooms.realestatemanager.fragment.list.contentprovider.ApartmentProviderContract.CONTENT_URI_APARTMENTS;
import static com.openclassrooms.realestatemanager.fragment.list.contentprovider.ApartmentProviderContract.CONTENT_URI_FILTERS;
import static com.openclassrooms.realestatemanager.fragment.list.contentprovider.ApartmentProviderContract.CONTENT_URI_IMAGES;
import static com.openclassrooms.realestatemanager.fragment.list.contentprovider.ApartmentProviderContract.IMAGE_COLUMN_APARTMENT_ID;
import static com.openclassrooms.realestatemanager.fragment.list.contentprovider.ApartmentProviderContract.IMAGE_COLUMN_URI;
import static com.openclassrooms.realestatemanager.fragment.list.contentprovider.ApartmentProviderContract.PATH_APARTMENTS;
import static com.openclassrooms.realestatemanager.fragment.list.contentprovider.ApartmentProviderContract.PATH_FILTERS;
import static com.openclassrooms.realestatemanager.fragment.list.contentprovider.ApartmentProviderContract.PATH_IMAGES;

public class ApartmentContentProvider extends ContentProvider {

    private static final String TAG = "ApartmentContentProvider";

    // Références aux DAOs
    private ApartmentDao apartmentDao;
    private FilterDao filterDao;
    private ImageDao imageDao;

    // Codes pour l'URI Matcher
    private static final int CODE_APARTMENTS_DIR = 1;
    private static final int CODE_APARTMENTS_ITEM = 2;
    private static final int CODE_FILTERS_DIR = 3;
    private static final int CODE_IMAGES_DIR = 4;
    private static final int CODE_IMAGES_ITEM = 5;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // URIs pour les appartements
        sUriMatcher.addURI(AUTHORITY, PATH_APARTMENTS, CODE_APARTMENTS_DIR);
        sUriMatcher.addURI(AUTHORITY, PATH_APARTMENTS + "/#", CODE_APARTMENTS_ITEM);
        // URIs pour les filtres
        sUriMatcher.addURI(AUTHORITY, PATH_FILTERS, CODE_FILTERS_DIR);
        // URIs pour les images
        sUriMatcher.addURI(AUTHORITY, PATH_IMAGES, CODE_IMAGES_DIR);
        sUriMatcher.addURI(AUTHORITY, PATH_IMAGES + "/#", CODE_IMAGES_ITEM);
    }

    @Override
    public boolean onCreate() {
        ApartmentDatabase database = ApartmentDatabase.getInstance(getContext());
        apartmentDao = database.apartmentDao();
        filterDao = database.filterDao();
        imageDao = database.imageDao();
        return true;
    }

    // -------------------------------------------------------------------------
    // QUERY
    // -------------------------------------------------------------------------

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;

        switch (sUriMatcher.match(uri)) {
            case CODE_APARTMENTS_DIR:
                cursor = apartmentDao.getAllApartmentsCursor();
                break;
            case CODE_APARTMENTS_ITEM:
                long apartmentId = ContentUris.parseId(uri);
                cursor = apartmentDao.getApartmentByIdCursor(apartmentId);
                break;
            case CODE_FILTERS_DIR:
                cursor = filterDao.getFilterCursor();
                break;
            case CODE_IMAGES_DIR:
                cursor = imageDao.getAllImagesCursor();
                break;
            case CODE_IMAGES_ITEM:
                long imageId = ContentUris.parseId(uri);
                cursor = imageDao.getImageByIdCursor(imageId);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    // -------------------------------------------------------------------------
    // INSERT
    // -------------------------------------------------------------------------

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long id = -1;
        Uri newUri = null;

        if (values == null) {
            throw new IllegalArgumentException("ContentValues cannot be null.");
        }

        switch (sUriMatcher.match(uri)) {
            case CODE_APARTMENTS_DIR:
                // Note: Tu devras adapter cette partie pour créer un objet Appartment à partir de ContentValues
                // et utiliser apartmentDao.insertApartment(appartment) qui retourne l'ID
                // Par exemple:
                // Appartment appartment = new Appartment(...);
                // id = apartmentDao.insertApartment(appartment);

                // Simuler l'insertion (à remplacer par le code réel)
                id = 1;
                newUri = ContentUris.withAppendedId(CONTENT_URI_APARTMENTS, id);
                break;

            case CODE_FILTERS_DIR:
                // Pour les filtres, on utilise insert avec REPLACE
                // Note: Tu devras créer un objet Filter à partir de ContentValues
                // filterDao.insertFilter(filter);

                // Le ContentProvider n'aura pas d'ID spécifique pour un Filter (puisque c'est un singleton dans ta DB)
                newUri = CONTENT_URI_FILTERS;
                break;

            case CODE_IMAGES_DIR:
                // Création de l'objet Image à partir de ContentValues
                String imgUrl = values.getAsString(IMAGE_COLUMN_URI);
                // Assure-toi que id_apartment existe dans ContentValues
                int idApartment = values.getAsInteger(IMAGE_COLUMN_APARTMENT_ID);

                Image image = new Image(idApartment, imgUrl);

                // Insertion de l'image et récupération de l'ID
                id = imageDao.insertImage(image);

                if (id > 0) {
                    newUri = ContentUris.withAppendedId(CONTENT_URI_IMAGES, id);
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI for insert: " + uri);
        }

        if (newUri != null) {
            // Notifier les observers en cas de changement
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return newUri;
    }

    // -------------------------------------------------------------------------
    // DELETE
    // -------------------------------------------------------------------------

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deletedRows = 0;

        switch (sUriMatcher.match(uri)) {
            case CODE_APARTMENTS_ITEM:
                long apartmentId = ContentUris.parseId(uri);
                Appartment apartmentToDelete = apartmentDao.getApartmentById(apartmentId);
                if (apartmentToDelete != null) {
                    apartmentDao.delete(apartmentToDelete);
                    deletedRows = 1;
                }
                break;

            case CODE_FILTERS_DIR:
                filterDao.deleteAll();
                deletedRows = 1; // On considère qu'une ligne (le filtre) a été affectée
                break;

            case CODE_IMAGES_ITEM:
                long imageId = ContentUris.parseId(uri);
                Image imageToDelete = imageDao.getImageById(imageId);
                if (imageToDelete != null) {
                    imageDao.deleteImage(imageToDelete);
                    deletedRows = 1;
                }
                break;

            case CODE_IMAGES_DIR:
                imageDao.deleteAllImage();
                deletedRows = 1; // Toutes les images supprimées
                break;

            default:
                throw new IllegalArgumentException("Unknown URI for delete: " + uri);
        }

        if (deletedRows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deletedRows;
    }

    // -------------------------------------------------------------------------
    // UPDATE
    // -------------------------------------------------------------------------

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int updatedRows = 0;

        if (values == null) {
            throw new IllegalArgumentException("ContentValues cannot be null for update.");
        }

        switch (sUriMatcher.match(uri)) {
            case CODE_APARTMENTS_ITEM:
                long apartmentId = ContentUris.parseId(uri);
                Appartment apartmentToUpdate = apartmentDao.getApartmentById(apartmentId);

                if (apartmentToUpdate != null) {
                    // Note: Tu devras implémenter la logique pour mettre à jour les champs de l'Appartment
                    // à partir de ContentValues et appeler apartmentDao.updateApartment(apartmentToUpdate)
                    // updatedRows = apartmentDao.updateApartment(apartmentToUpdate);
                    updatedRows = 1; // Simulé
                }
                break;

            case CODE_IMAGES_ITEM:
                long imageId = ContentUris.parseId(uri);
                Image imageToUpdate = imageDao.getImageById(imageId);

                if (imageToUpdate != null) {
                    if (values.containsKey(IMAGE_COLUMN_URI)) {
                        imageToUpdate.setImgUrl(values.getAsString(IMAGE_COLUMN_URI));
                    }
                    if (values.containsKey(IMAGE_COLUMN_APARTMENT_ID)) {
                        imageToUpdate.setIdApartment(values.getAsInteger(IMAGE_COLUMN_APARTMENT_ID));
                    }

                    updatedRows = imageDao.updateImage(imageToUpdate);
                }
                break;

            case CODE_FILTERS_DIR:
                // Pour les filtres, l'insertion (avec REPLACE) est souvent utilisée comme une mise à jour.
                throw new UnsupportedOperationException("Update not supported for filters. Use insert.");

            default:
                throw new UnsupportedOperationException("Update operation not supported for this URI: " + uri);
        }

        if (updatedRows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updatedRows;
    }

    // -------------------------------------------------------------------------
    // getType
    // -------------------------------------------------------------------------

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case CODE_APARTMENTS_DIR:
                return ApartmentProviderContract.CONTENT_TYPE_APARTMENTS;
            case CODE_APARTMENTS_ITEM:
                return ApartmentProviderContract.CONTENT_ITEM_TYPE_APARTMENT;
            case CODE_FILTERS_DIR:
                return ApartmentProviderContract.CONTENT_TYPE_FILTERS;
            case CODE_IMAGES_DIR:
                return ApartmentProviderContract.CONTENT_TYPE_IMAGES;
            case CODE_IMAGES_ITEM:
                return ApartmentProviderContract.CONTENT_ITEM_TYPE_IMAGE;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}