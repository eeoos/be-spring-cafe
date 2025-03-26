package codesquad.codestagram.user.service;

import codesquad.codestagram.user.domain.User;
import codesquad.codestagram.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("UserService: 회원 관리 기능 테스트")
class UserServiceIntegrationTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원가입 후 동일한 seq로 조회하면 등록된 회원 정보를 반환해야 한다.")
    void join() {
        // given: 테스트용 User 객체 생성
        User user = new User(
                "testUserId1",
                "testPassword1!",
                "testName1",
                "testEmail1"
        );

        // when: User 객체 등록, 조회
        User savedUser = userService.join(user);
        User findUser = userService.findUser(savedUser.getSeq());

        compare(user, findUser);
    }

    @Test
    @DisplayName("저장되지 않은 User 객체를 조회하면 NoSuchElementException 예외가 발생해야 한다.")
    void findUser() {
        //given: 테스트용 User 객체 생성
        User user = new User(
                "testUserId1",
                "testPassword1!",
                "testName1",
                "testEmail1"
        );
        user.setSeq(10000000000L);

        // when: 저장되지 않은 테스트용 User의 아이디를 통해 조회할 때
        // then: NoSuchElementException 예외가 터져야 함.
        assertThatThrownBy(() -> userService.findUser(user.getSeq()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    /*@Test
    @DisplayName("저장된 모든 회원 목록이 올바른 순서로 반환되어야 한다.")
    void findUsers() {
        // given: 테스트용 User 객체 4개 생성 후 저장
        User user1 = new User("testUserId1", "testPassword1!", "testName1", "testEmail1");
        User user2 = new User("testUserId2", "testPassword2!", "testName2", "testEmail2");
        User user3 = new User("testUserId3", "testPassword3!", "testName3", "testEmail3");
        User user4 = new User("testUserId4", "testPassword4!", "testName4", "testEmail4");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);

        // when: 저장된 User 객체 리스트 조회
        List<User> result = userRepository.findAll();

        // then: 조회된 User 객체 리스트가 테스트용 User 객체 리스트와 사이즈, 참조값이 동일해야 함
        ArrayList<User> expectUsers = new ArrayList<>(List.of(user1, user2, user3, user4));
        for (int i = 0; i <expectUsers.size(); i++) {
            compare(expectUsers.get(i), result.get(i));
        }
        assertThat(result).hasSize(4);

    }*/


    private void compare(User actual, User expect) {
        // 의문: hashCode와 equals 오버라이딩해서 동등성 비교해도 되지 않나?
        assertThat(actual.getUserId()).isEqualTo(expect.getUserId());
        assertThat(actual.getPassword()).isEqualTo(expect.getPassword());
        assertThat(actual.getName()).isEqualTo(expect.getName());
        assertThat(actual.getEmail()).isEqualTo(expect.getEmail());
    }
}
