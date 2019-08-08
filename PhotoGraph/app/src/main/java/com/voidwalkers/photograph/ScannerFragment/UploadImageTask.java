package com.voidwalkers.photograph.ScannerFragment;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.method.CharacterPickerDialog;
import android.widget.TextView;

import com.voidwalkers.photograph.ScannerFragment.api.request.SingleProcessRequest;
import com.voidwalkers.photograph.ScannerFragment.api.response.DetectionResult;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class UploadImageTask extends AsyncTask<UploadImageTask.UploadParams, Void, UploadImageTask.Result> {

    private final ResultListener listener;

    UploadImageTask(ResultListener listener) {
        this.listener = listener;
    }

    @Override
    protected Result doInBackground(UploadParams... arr) {
        UploadParams params = arr[0];
        Result result;
        try {
            OkHttpClient client = new OkHttpClient();
            SingleProcessRequest singleProcessRequest = new SingleProcessRequest(params.image);
            MediaType JSON = MediaType.parse("application/json");
            RequestBody requestBody = RequestBody.create(JSON, new Gson().toJson(singleProcessRequest));

            Request request = new Request.Builder()
                    .url("https://api.mathpix.com/v3/latex")
                    .addHeader("content-type", "application/json")
                    .addHeader("app_id", "mathpix")
                    .addHeader("app_key", "139ee4b61be2e4abcfb1238d9eb99902")
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            String responseString = response.body().string();
            DetectionResult detectionResult = new Gson().fromJson(responseString, DetectionResult.class);
            if (detectionResult != null && detectionResult.latex != null) {
                result = new ResultSuccessful(detectionResult.latex);
            } else if (detectionResult != null && detectionResult.error != null) {
                result = new ResultFailed(detectionResult.error);
            } else {
                result = new ResultFailed("Math not found");
            }
        } catch (Exception e) {
            result = new ResultFailed("Failed to send to server. Check your connection and try again");
        }
        return result;
    }

    @Override
    protected void onPostExecute(Result result) {
        if (result instanceof ResultSuccessful) {
            ResultSuccessful successful = (ResultSuccessful) result;
            listener.onSuccess(successful.latex);
        } else if (result instanceof ResultFailed) {
            ResultFailed failed = (ResultFailed) result;
            listener.onError(failed.message);
        }
    }

    interface ResultListener {
        void onError(String message);

        void onSuccess(String url);
    }

    static class UploadParams {
        private Bitmap image;

        UploadParams(Bitmap image) {
            this.image = image;
        }
    }

    static class Result {
    }

    private static class ResultSuccessful extends Result {
        String latex;

        ResultSuccessful(String latex) {

            //latex = Sysofeq(latex);

            this.latex = latex;
        }

        private String Sysofeq(String latex) {
            String[] split = latex.split(",");
            String eqone = split[0];
            String eqtwo = split[1];
            int[] one = new int[3];
            int[] two = new int[3];

            int j = 0;
            int x = 0;
            int var = 1;
            int count = 0;

            for (int i = 0;i<eqone.length();i++) {
                char c = eqone.charAt(i);
                if (c == '=') {
                    var = -1;
                }

                if (Character.isDigit(c)) {
                    x = x * 10 + Character.getNumericValue(c);
                }

                if (Character.isLetter(c)) {
                    one[j] = x * var;
                    x = 0;
                    j++;
                    count++;

                }
                if (count == 2 && i == eqone.length() - 1 ) {
                    one[j] = x * var;
                    j++;
                    x = 0;
                }
            }
            j = 0;
            x = 0;
            var = 1;
            count = 0;


            for (int i = 0;i<eqtwo.length();i++) {
                char c = eqtwo.charAt(i);
                if (c == '=') {
                    var = -1;
                }

                if (Character.isDigit(c)) {
                    x = x * 10 + Character.getNumericValue(c);
                }

                if (Character.isLetter(c)) {
                    two[j] = x * var;
                    x = 0;
                    j++;
                    count++;
                }
                if (count == 2 && i == eqtwo.length() - 1 ) {
                    two[j] = x * var;
                    j++;
                    x = 0;
                }
            }

            int a1 = one[0];
            int b1 = one[1];
            int c1 = one[2];
            int a2 = two[0];
            int b2 = two[1];
            int c2 = two[2];

            double q =  (c2*a1 - c1*a2)*1.0/(b1*a2 - a1*b2);
            double p =  (b2*c1 - b1*c2)*1.0/(b1*a2 - a1*b2);

//           String ans = String.valueOf(a1)+ "a" +String.valueOf(b1) + "b" + String.valueOf(c1) + "c" + String.valueOf(a2) + "d"+ String.valueOf(b2)+"e" + String.valueOf(c2);

            String ans = "x = " + String.valueOf(p) +",y = " + String.valueOf(q);
            return ans;
        }
    }

    private static class ResultFailed extends Result {
        String message;

        ResultFailed(String message) {
            this.message = message;
        }
    }
}
