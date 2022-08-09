package sketcher.scheduling.algorithm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Rollback(value = false)
public class AutoSchedulingTest {

    int scheduleNodeSize = 4;
    int managerNodeSize = 20;

    


    @Test
    public void dfsTest() {

    }

    public boolean dfs() {
        for(int i = 0; i < a[x].size(); i++) {
            int t = a[x][i];
            if(c[t]) continue;
            c[t] = true;
            if(d[t] == 0 || dfs(d[t])) {
                d[t] = x;
                return true;
            }
        }
        return false;
    }

    /*
#define MAX 1001

vector<int> a[MAX];
int d[MAX];
bool c[MAX];
int n, m;

bool dfs(int x) {
	for(int i = 0; i < a[x].size(); i++) {
		int t = a[x][i];
		if(c[t]) continue;
		c[t] = true;
		if(d[t] == 0 || dfs(d[t])) {
			d[t] = x;
			return true;
		}
	}
	return false;
}

int main(void) {
	scanf("%d %d", &n, &m);
	for(int i = 1; i <= n; i++) {
		int k;
		scanf("%d", &k);
		for(int j = 1; j <= k; j++) {
			int t;
			scanf("%d", &t);
			a[i].push_back(t);
		}
	}
	int count = 0;
	for(int i = 1; i <= n; i++) {
		fill(c, c + MAX, false);
		if(dfs(i)) count++;
	}
	cout << count << '\n';
	return 0;
}*/
}