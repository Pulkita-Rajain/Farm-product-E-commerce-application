package com.app.ecom.service;

import com.app.ecom.Repository.UserRepository;
import com.app.ecom.dto.AddressDTO;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.Address;
import com.app.ecom.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;
   // private final List<User> userList = new ArrayList<>();

    private Long nextId = 1L;

    public List<UserResponse> fetchAllUsers(){
        //List<User> userList = userRepository.findAll();
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());

    }
    public void addUser(UserRequest userRequest){
        //user.setId(nextId++);
        User user = new User();
        UpdateUserFromRequest(user, userRequest);
        userRepository.save(user);

    }



    public Optional<UserResponse> fetchUser(Long id) {
        /*return userList.stream().
                filter(user -> user.getId().equals(id))
                .findFirst();*/
        return userRepository.findById(id)
                .map(this::mapToUserResponse);

    }
    public boolean updateUser(Long id, UserRequest updateUserRequest){
        /*return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(existingUser -> {
                    existingUser.setFirstName(updateUser.getFirstName());
                    existingUser.setLastName(updateUser.getLastName());
                    return true;
                }).orElse(false); */
        return userRepository.findById(id)
                .map(existingUser -> {
                    UpdateUserFromRequest(existingUser, updateUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }
    private void UpdateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());

        if(userRequest.getAddressDto() != null){
            Address address = new Address();
            address.setStreet(userRequest.getAddressDto().getStreet());
            address.setCity(userRequest.getAddressDto().getCity());
            address.setState(userRequest.getAddressDto().getState());
            address.setCountry(userRequest.getAddressDto().getCountry());
            address.setPincode(userRequest.getAddressDto().getPincode());
            user.setAddress(address);
        }
    }
    private UserResponse mapToUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setUserRole(user.getRole());

        if(user.getAddress() != null){
            AddressDTO addressDto = new AddressDTO();
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setState(user.getAddress().getState());
            addressDto.setCountry(user.getAddress().getCountry());
            addressDto.setPincode(user.getAddress().getPincode());
            response.setAddressDto(addressDto);
        }
        return response;

    }
}
