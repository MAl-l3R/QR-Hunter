

package com.example.snailscurlup.model;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

/* TOdO: implement listener so asynchonous operation  work */

/**
 * Database
 *
 * Represents a connection to the Firebase database that the app needs to run.
 * Intended to abstract away certain database operations and make it easier to
 * integrate database operations with the rest of the app.
 *
 * @author Ayan123
 */
public class Database {

    private static Database instance;
    private FirebaseFirestore db;
    private CollectionReference usersRef;
    private DocumentSnapshot activeUserDoc;

    private Database() {
        db = FirebaseFirestore.getInstance();
        usersRef = db.collection("users");
    }

    /**
     * Returns the instance of the database (which is global).
     * @return An instance of the database.
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * Uploads user info to the database. Stores user information in Firebase.
     * @param user - An object containing all of the user information.
     */
    public void addUser(User user) {
        HashMap<String, Object> userdata = new HashMap<>();
        userdata.put("email", user.getEmail());
        userdata.put("phoneNumber", user.getPhoneNumber());
        userdata.put("profilePhotoPath", user.getProfilePhotoPath());
        userdata.put("device_id", user.getDevice_id());

        usersRef
                .document(user.getUsername())
                .set(userdata)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // log message for success
                        Log.d(TAG, "User added successfully");


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // log message with error when data failed to be added
                        Log.d(TAG, "User not added: " + e);
                    }
                });

    }

    /**
     * Deletes a user's data from the database.
     * @param user - Object referencing the user to be deleted
     */
    public void removeUser(User user) {
        usersRef
                .document(user.getUsername())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // log message for success
                        Log.d(TAG, "User removed successfully");


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // log message with error when data failed to be added
                        Log.d(TAG, "User not removed: " + e);
                    }
                });

    }

    /* refeernce link https://medium.com/firebase-developers/why-are-firebase-apis-asynchronous-callbacks-promises-tasks-e037a6654a93 */

    /**
     * Used to check if an account with a certain username has already been registered.
     * Warns user if the username is already taken, and disallows account creation.
     * @param username - The username (String) to check if already registered.
     * @return Whether or not the username is available (False) or taken (True)
     */
    public boolean isUsernameTaken(String username) {
        boolean isTaken = false;
        Task task = usersRef.document(username).get();
        try {
            DocumentSnapshot documentSnapshot = (DocumentSnapshot) Tasks.await(task);
            if (documentSnapshot.exists()) {
                isTaken = true; // Username already taken
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            Log.d(TAG, "Error occurred while checking username availability", e);
        }
        return isTaken; // Username is available or not available
    }


    /**
     * Fetches the User object associated with a certain username from the database.
     * @param username - The username whose data is to be retrieved
     * @return - a User object containing all info tied to the username
     */
    public User getUserByUsername(String username) {
        final User[] user = {null};

        usersRef
                .document(username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            DocumentSnapshot userDoc = task.getResult();
                            if (userDoc.exists()) {
                                String userEmail = userDoc.getString("email");
                                String userPhoneNumber = userDoc.getString("phoneNumber");
                                String userPhotoPath = userDoc.getString("profilePhotoPath");
                                String userDeviceId = userDoc.getString("device_id");
                                user[0] = new User(username, userEmail, userPhoneNumber, userPhotoPath, userDeviceId);
                            } else {
                                user[0] = null;
                            }
                        }
                    }
                });
        return user[0];
    }


                /*.addOnSuccessListener(
                        new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                if (documentSnapshot.exists()) {
                                    String email = documentSnapshot.getString("email");
                                    String phoneNumber = documentSnapshot.getString("phoneNumber");
                                    String profilePhotoPath = documentSnapshot.getString("profilePhotoPath");
                                    String device_id = documentSnapshot.getString("device_id");
                                    user = new User(username, email, phoneNumber, profilePhotoPath, device_id);
                                    return user;
                                } else {
                                    return null;
                                }
                            }
                        }
                )
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "Error getting document: " + e);
                                return null;
                            }
                        }
                ); */


    /**
     * Updates a user's information in the database.
     * @param user - The User object containing the information that will
     *             overwrite the previous information.
     */
    public void updateUser(User user) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("email", user.getEmail());
        data.put("phoneNumber", user.getPhoneNumber());
        data.put("profilePhotoPath", user.getProfilePhotoPath());
        data.put("device_id", user.getDevice_id());

        /* worry about change username later
        if (!user.getUsername().equals(oldUsername)) {
            usersRef
                    .document(user.getUsername())
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            // log message for success
                            Log.d(TAG, "User removed successfully");

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // log message with error when data failed to be added
                            Log.d(TAG, "User not removed: " + e);
                        }
                    });
            addUser(user);

        } else if (user.getUsername() == oldUsername) { */
            usersRef
                    .document(user.getUsername())
                    .update(data)
                    .addOnSuccessListener(
                            new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "User updated successfully");


                                }
                            }
                    )
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "User not updated: " + e);
                                }
                            }
                    );




    }

    /**
     * Sets which user/account is currently active within the app.
     * @param user - The user who is supposed to be currently active
     */
    public void setActiveUser(User user) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("username", user.getUsername());

        usersRef.document("active")
                .set(data)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "Active user set successfully");

                            }
                        }
                )
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "Active user not set: " + e);
                            }
                        }
                );
    }

    /**
     * Fetches the currently active user within the instance of the app.
     * @return The User object for the active user
     */
    public User getActiveUser() {
        final User[] activeUser = {null};
        usersRef.document("active")
                .get()
                .addOnSuccessListener(
                        new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    String activeUsername = documentSnapshot.getString("username");
                                    User user = getUserByUsername(activeUsername);
                                    if (user != null) {
                                        activeUser[0] = user;
                                    }
                                }


                            }
                        }
                )
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "Error getting active user: " + e);
                            }
                        });

        return activeUser[0];
        }

    /**
     * Fetches a list of all users currently stored in the database.
      * @return a List object containing all User objects in the DB.
     */
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>(); // create empty list to add users to

        usersRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String username = documentSnapshot.getId();
                            String userEmail = documentSnapshot.getString("email");
                            String userPhoneNumber = documentSnapshot.getString("phoneNumber");
                            String userPhotoPath = documentSnapshot.getString("profilePhotoPath");
                            String userDeviceId = documentSnapshot.getString("device_id");
                            User user = new User(username, userEmail, userPhoneNumber, userPhotoPath, userDeviceId);
                            userList.add(user);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Error getting all users: " + e);
                    }
                });

        return userList;
    }

    /**
     * Deletes the active user from the database.
     */
    public void removeActiveUser() {
        usersRef.document("active")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Active user removed successfully");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Active user not removed: " + e);
                    }
                });
    }

}
