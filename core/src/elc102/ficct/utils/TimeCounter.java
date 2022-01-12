package elc102.ficct.utils;

public class TimeCounter {

  private long updateTime;
  private long lastTime;

  private boolean isEnabled;

  public TimeCounter(long updateTime) {
    this.updateTime = updateTime;
    this.lastTime = 0;
    this.isEnabled = true;
  }

  public boolean hasPassed() {
    return System.currentTimeMillis() - lastTime >= updateTime && isEnabled;
  }

  public void reset() {
    lastTime = System.currentTimeMillis();
  }

  public void stop(){
    isEnabled = false;
  }
}
