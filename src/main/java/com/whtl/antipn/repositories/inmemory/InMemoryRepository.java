//package com.whtl.antipn.repositories.inmemory;
//
//import com.whtl.antipn.exception.EntityNotFoundException;
//import com.whtl.antipn.model.*;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//@Repository
//public class InMemoryRepository {
//
//    private final Map<Integer, User> mapUsers = new HashMap<>();
//    private final Map<Integer, Role> mapRoles = new HashMap<>();
//    private final Map<Integer, Restaurant> mapRestaurants = new HashMap<>();
//    private final Map<Integer, List<Menu>> mapMenus = new HashMap<>(); //rest id and List menu
//
//    private final Map<LocalDate, Map<Integer, List<Menu>>> mapMenuByDates = new HashMap<>();  //map <Date map <resId, List<Menu>>
//
//    private final Map<LocalDate, HashSet<Vote>> mapVotes = new HashMap<>(); //map LocalDate and Set his votes
//
//    {
//        //users
//
//        User admin = new User(0, "CNC Admin ", "admin@email.ru", "password", true, new Date(), Set.of(Role.ADMIN));
//        User user1 = new User(0, "User 1", "user_1@email.ru", "password", true, new Date(), Set.of(Role.USER));
//        User user2 = new User(0, "User 2 ", "user_2@email.ru", "password", true, new Date(), Set.of(Role.USER));
//
//        mapUsers.put(0, admin);
//        mapUsers.put(1, user1);
//        mapUsers.put(2, user2);
//
//        //restaurants
//
//        Restaurant restaurant1 = new Restaurant(1, "Grand Hotel", "Address1", true);
//        Restaurant restaurant2 = new Restaurant(2, "Palace", "Address2", true);
//        Restaurant restaurant3 = new Restaurant(3, "Astoria", "Address3", true);
//
//        mapRestaurants.put(1, restaurant1);
//        mapRestaurants.put(2, restaurant2);
//        mapRestaurants.put(3, restaurant3);
//
//        // menus
//
//        Menu menu1 = new Menu(1, LocalDate.now(), restaurant1, "Breakfast", 1000.0);
//        Menu menu2 = new Menu(2, LocalDate.now(), restaurant1, "Lunch", 2000.0);
//        Menu menu3 = new Menu(3, LocalDate.now(), restaurant1, "Dinner", 3000.0);
//        List<Menu> menuRestOne = List.of(menu1, menu2, menu3);
//
//        Menu menu4 = new Menu(4, LocalDate.now(), restaurant2, "Breakfast", 2000.0);
//        Menu menu5 = new Menu(5, LocalDate.now(), restaurant2, "Lunch", 3000.0);
//        Menu menu6 = new Menu(6, LocalDate.now(), restaurant2, "Dinner", 4000.0);
//        List<Menu> menuRestTwo = List.of(menu4, menu5, menu6);
//
//        Menu menu7 = new Menu(7, LocalDate.now(), restaurant3, "Breakfast", 3000.0);
//        Menu menu8 = new Menu(8, LocalDate.now(), restaurant3, "Lunch", 4000.0);
//        Menu menu9 = new Menu(9, LocalDate.now(), restaurant3, "Dinner", 5000.0);
//        List<Menu> menuRestThree = List.of(menu7, menu8, menu9);
//
//        mapMenus.put(1, menuRestOne);
//        mapMenus.put(2, menuRestTwo);
//        mapMenus.put(3, menuRestThree);
//
//        //меню на дни одинаковые
//        mapMenuByDates.put(LocalDate.now(), mapMenus);
//        mapMenuByDates.put(LocalDate.now().minusDays(1), mapMenus);
//        mapMenuByDates.put(LocalDate.now().minusDays(2), mapMenus);
//
//        //votes
//        mapVotes.put(LocalDate.now(), new HashSet<>(Arrays.asList(new Vote(5, LocalDate.now(), user1, restaurant1), new Vote(6, LocalDate.now(), user2, restaurant2))));
//        mapVotes.put(LocalDate.now().minusDays(1), new HashSet<>(Arrays.asList(new Vote(3, LocalDate.now().minusDays(1), user1, restaurant2), new Vote(4, LocalDate.now().minusDays(1), user2, restaurant2))));
//        mapVotes.put(LocalDate.now().minusDays(2), new HashSet<>(Arrays.asList(new Vote(1, LocalDate.now().minusDays(2), user1, restaurant1), new Vote(2, LocalDate.now().minusDays(2), user2, restaurant1))));
//    }
//
//    public void showMaps() {//rempopary method for showing in console our data
//        mapRestaurants.forEach((k, v) -> System.out.println(k + " " + v));
//        mapMenus.forEach((k, v) -> System.out.println(k + " " + v));
//        mapUsers.forEach((k, v) -> System.out.println(k + " " + v));
//        mapRoles.forEach((k, v) -> System.out.println(k + " " + v));
//        mapVotes.forEach((k, v) -> System.out.println(k + " " + v));
//        // mapRestaurantsScore.forEach((k, v) -> System.out.println(k + " " + v));
//    }
//
//    //restaurants
//    public List<Restaurant> findAllRestaurants() {
//        //System.out.println("Нашли рестораны в базе");
//        List<Restaurant> result = new ArrayList<>();
//        return mapRestaurants.values().stream().toList();
//    }
//
//    public Restaurant findRestaurantById(int id) {
//        showMaps();
//        return mapRestaurants.get(id);
//    }
//
//    public Restaurant saveRestaurant(Restaurant restaurant) {
//        if (restaurant.getId() == null) {
//            int id = mapRestaurants.size() + 1;
//            restaurant.setId(id);
//        }
//        mapRestaurants.put(restaurant.getId(), restaurant);
//        return mapRestaurants.get(restaurant.getId());
//
//    }
//
//    public boolean deleteRestaurant(int restId) { //discuss it
//        return mapRestaurants.remove(restId) != null;
//    }
//
//    //rating
//
//    public Map<Integer, Integer> findAllRestaurantsScoreOnToday(LocalDate localDate) {
//        Set<Vote> votesOnToday = mapVotes.get(localDate); //votes not found -> exception
//        if (votesOnToday == null) {
//            throw new EntityNotFoundException("Rating", 0, "There is no rating on date = " + localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
//        }
//        List<Integer> onlyVotesByRestId = new ArrayList<>();
//        //receives only votes on today which ones are just List of restId
//        votesOnToday
//                .forEach(item -> onlyVotesByRestId.add(item.getRestaurant().getId())); //fixed
//
//        //map restId, restId repeat times
//        Map<Integer, Integer> scores = new HashMap<>();
//        onlyVotesByRestId
//                .forEach(item -> scores.put(item, Collections.frequency(onlyVotesByRestId, item)));
//        //scores.forEach((k, v) -> System.out.println("rest_id = " + k + " score on today = " + v));
//        return scores;
//    }
//
//    public List<Menu> findMenuByRestId(int restId) {
//        return mapMenuByDates.get(LocalDate.now()).get(restId);
//    }
//
//    public List<Menu> findMenuByRestIdAndDate(LocalDate localDate, int restId) {
//        if (!mapMenuByDates.containsKey(localDate)) {
//            return null;
//        }
//        return mapMenuByDates.get(localDate).get(restId);
//    }
//
//    public boolean deleteMenuByRestIdAndDate(LocalDate localDate, int restId) {
//        if (!mapMenuByDates.containsKey(localDate)) {
//            return false;
//        }
//        return mapMenuByDates.get(localDate).remove(restId) != null;
//    }
//
//    public List<Menu> saveMenu(LocalDate localDate, int restId, List<Menu> menuList) {
//
//        if (!mapMenuByDates.containsKey(localDate)) {
//            Map<Integer, List<Menu>> map = new HashMap<>();
//            mapMenuByDates.put(localDate, map);
//        }
//
//        mapMenuByDates.get(localDate).put(restId, menuList);
//        List<Menu> result = mapMenuByDates.get(localDate).get(restId);
//        return result;
//    }
//
//    public Set<Vote> findVotesOnDate(LocalDate localDate) { //discuss it
//        return mapVotes.get(localDate);
//    }
//
//    public boolean saveVoteSpecial(Vote vote) {
//        if (!mapVotes.containsKey(LocalDate.now())) {
//            HashSet<Vote> set = new HashSet<>();
//            mapVotes.put(LocalDate.now(), set);
//        }
//        return mapVotes.get(LocalDate.now()).add(vote);
//    }
//
//    public boolean deleteVoteSpecial(Vote vote) {
//        if (mapVotes.get(LocalDate.now()) == null) {
//            return false;
//        }
//        return mapVotes.get(LocalDate.now()).remove(vote);
//    }
//
//    public Vote findVote(int userId) {
//        LocalDate today = LocalDate.now();
//        Set<Vote> todayVotes = mapVotes.get(today);
//        if (todayVotes == null) {
//            return null;
//            //throw new EntityNotFoundException("VoteDto", userId, "There is no vote for user"); //спорное решение
//        }
//        Vote usersVoteToday = null;
//        for (Vote item : todayVotes) {
//            if (item.getUser().getId() == userId) { //fixed
//                usersVoteToday = item;
//            }
//        }
//        return usersVoteToday;
//    }
//
//    public User findUserById(int userId) {
//        return mapUsers.get(userId);
//    }
//
//}
