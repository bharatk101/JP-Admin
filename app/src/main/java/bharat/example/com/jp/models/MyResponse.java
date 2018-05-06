package bharat.example.com.jp.models;

import java.util.List;

/**
 * Created by bharat on 3/1/18.
 */

public class MyResponse {

    public  long multicast_id;
    public int success;
    public  int failure;
    public  int canonical_id;
    public List<Result> results;

    public MyResponse() {
    }

    public MyResponse(long multicast_id, int success, int failure, int canonical_id, List<Result> results) {
        this.multicast_id = multicast_id;
        this.success = success;
        this.failure = failure;
        this.canonical_id = canonical_id;
        this.results = results;
    }

    public long getMulticast_id() {
        return multicast_id;
    }

    public void setMulticast_id(long multicast_id) {
        this.multicast_id = multicast_id;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }

    public int getCanonical_id() {
        return canonical_id;
    }

    public void setCanonical_id(int canonical_id) {
        this.canonical_id = canonical_id;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
