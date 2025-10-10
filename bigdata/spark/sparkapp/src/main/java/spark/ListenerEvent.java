package spark;

import lombok.Data;

import java.util.Map;

@Data
public class ListenerEvent {

  String appId;
  String appName;
  String name;
  String createdTimestamp;

  Map<String, Object> extra;

  public ListenerEvent putExtra(String key, Object value) {
    extra.put(key, value);
    return this;
  }
}
