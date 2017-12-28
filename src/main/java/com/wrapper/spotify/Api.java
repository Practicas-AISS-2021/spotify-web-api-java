package com.wrapper.spotify;

import com.google.common.base.Joiner;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.model_objects.PlaylistTrackPosition;
import com.wrapper.spotify.requests.AbstractRequest;
import com.wrapper.spotify.requests.authentication.AuthorizationCodeGrantRequest;
import com.wrapper.spotify.requests.authentication.AuthorizationUriRequest;
import com.wrapper.spotify.requests.authentication.ClientCredentialsGrantRequest;
import com.wrapper.spotify.requests.authentication.RefreshAccessTokenRequest;
import com.wrapper.spotify.requests.data.albums.GetAlbumRequest;
import com.wrapper.spotify.requests.data.albums.GetAlbumsTracksRequest;
import com.wrapper.spotify.requests.data.albums.GetSeveralAlbumsRequest;
import com.wrapper.spotify.requests.data.artists.*;
import com.wrapper.spotify.requests.data.browse.*;
import com.wrapper.spotify.requests.data.browse.miscellaneous.GetAvailableGenreSeedsRequest;
import com.wrapper.spotify.requests.data.follow.UnfollowPlaylistRequest;
import com.wrapper.spotify.requests.data.library.CheckUsersSavedTracksRequest;
import com.wrapper.spotify.requests.data.library.GetUsersSavedTracksRequest;
import com.wrapper.spotify.requests.data.library.RemoveUsersSavedTracksRequest;
import com.wrapper.spotify.requests.data.library.SaveTracksForUserRequest;
import com.wrapper.spotify.requests.data.player.GetUsersCurrentlyPlayingTrackRequest;
import com.wrapper.spotify.requests.data.player.GetUsersRecentlyPlayedTracksRequest;
import com.wrapper.spotify.requests.data.playlists.*;
import com.wrapper.spotify.requests.data.search.AlbumSearchRequest;
import com.wrapper.spotify.requests.data.search.ArtistSearchRequest;
import com.wrapper.spotify.requests.data.search.PlaylistSearchRequest;
import com.wrapper.spotify.requests.data.search.TrackSearchRequest;
import com.wrapper.spotify.requests.data.tracks.GetAudioFeaturesForTrackRequest;
import com.wrapper.spotify.requests.data.tracks.GetSeveralTracksRequest;
import com.wrapper.spotify.requests.data.tracks.GetTrackRequest;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import com.wrapper.spotify.requests.data.users_profile.GetUsersProfileRequest;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

/**
 * Instances of the Api class provide access to the Spotify Web API.
 */
public class Api {

  /**
   * The default host of Spotify API calls.
   */
  public static final String DEFAULT_HOST = "api.spotify.com";

  /**
   * The default port of Spotify API calls.
   */
  public static final int DEFAULT_PORT = 443;

  /**
   * A HttpManager configured with default settings.
   */
  public static final HttpManager DEFAULT_HTTP_MANAGER = SpotifyHttpManager.builder().build();

  /**
   * The default http scheme of Spotify API calls.
   */
  public static final String DEFAULT_SCHEME = "https";

  public static final String DEFAULT_AUTHENTICATION_HOST = "accounts.spotify.com";

  public static final int DEFAULT_AUTHENTICATION_PORT = 443;

  public static final String DEFAULT_AUTHENTICATION_SCHEME = "https";

  /**
   * Api instance with the default settings.
   */
  public static final Api DEFAULT_API = Api.builder().build();
  private final String clientId;
  private final String clientSecret;
  private final String redirectURI;
  private HttpManager httpManager = null;
  private String scheme;
  private int port;
  private String host;
  private String accessToken;
  private String refreshToken;

  private Api(Builder builder) {
    assert (builder.host != null);
    assert (builder.port > 0);
    assert (builder.scheme != null);


    if (builder.httpManager == null) {
      this.httpManager = SpotifyHttpManager
              .builder()
              .build();
    } else {
      this.httpManager = builder.httpManager;
    }
    scheme = builder.scheme;
    host = builder.host;
    port = builder.port;
    accessToken = builder.accessToken;
    refreshToken = builder.refreshToken;
    clientId = builder.clientId;
    clientSecret = builder.clientSecret;
    redirectURI = builder.redirectURI;
  }

  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns a an album with the id given below.
   *
   * @param id The base62 id of the album you're trying to retrieve.
   * @return An {AlbumRequest.Builder} instance.
   */
  public GetAlbumRequest.Builder getAlbum(String id) {
    GetAlbumRequest.Builder builder = GetAlbumRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    builder.id(id);
    return builder;
  }

  public GetSeveralAlbumsRequest.Builder getAlbums(String... ids) {
    GetSeveralAlbumsRequest.Builder builder = GetSeveralAlbumsRequest.builder();
    builder.ids(ids);
    builder.setDefaults(httpManager, scheme, host, port);
    return builder;
  }

  public GetArtistsAlbumsRequest.Builder getAlbumsForArtist(String artistId) {
    GetArtistsAlbumsRequest.Builder builder = GetArtistsAlbumsRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    builder.id(artistId);
    return builder;
  }

  public GetAlbumsTracksRequest.Builder getTracksForAlbum(
          String albumId
  ) {
    GetAlbumsTracksRequest.Builder builder = GetAlbumsTracksRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    builder.id(albumId);
    return builder;
  }

  public GetArtistRequest.Builder getArtist(String id) {
    GetArtistRequest.Builder builder = GetArtistRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    builder.id(id);
    return builder;
  }

  public GetSeveralArtistsRequest.Builder getArtists(String... ids) {
    GetSeveralArtistsRequest.Builder builder = GetSeveralArtistsRequest.builder();
    builder.ids(ids);
    builder.setDefaults(httpManager, scheme, host, port);
    return builder;
  }

  public GetTrackRequest.Builder getTrack(String id) {
    GetTrackRequest.Builder builder = GetTrackRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    builder.id(id);
    return builder;
  }

  public GetSeveralTracksRequest.Builder getTracks(String... ids) {
    return getTracks(Arrays.asList(ids));
  }

  public GetSeveralTracksRequest.Builder getTracks(List<String> ids) {
    GetSeveralTracksRequest.Builder builder = GetSeveralTracksRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    builder.id(ids);
    return builder;
  }

  public GetRecommendationsRequest.Builder getRecommendations() {
    GetRecommendationsRequest.Builder builder = GetRecommendationsRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    return builder;
  }

  public AlbumSearchRequest.Builder searchAlbums(String query) {
    AlbumSearchRequest.Builder builder = AlbumSearchRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    builder.query(query);
    return builder;
  }

  public TrackSearchRequest.Builder searchTracks(String query) {
    TrackSearchRequest.Builder builder = TrackSearchRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    builder.query(query);
    return builder;
  }

  public ArtistSearchRequest.Builder searchArtists(String query) {
    ArtistSearchRequest.Builder builder = ArtistSearchRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    builder.query(query);
    return builder;
  }

  public PlaylistSearchRequest.Builder searchPlaylists(String query) {
    PlaylistSearchRequest.Builder builder = PlaylistSearchRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    builder.query(query);
    return builder;
  }

  public GetListOfNewReleasesRequest.Builder getNewReleases() {
    GetListOfNewReleasesRequest.Builder builder = GetListOfNewReleasesRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    return builder;
  }

  public GetAudioFeaturesForTrackRequest.Builder getAudioFeature(String id) {
    GetAudioFeaturesForTrackRequest.Builder builder = GetAudioFeaturesForTrackRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    builder.id(id);
    return builder;
  }

  public GetUsersRecentlyPlayedTracksRequest.Builder getRecentlyPlayedTracks() {
    GetUsersRecentlyPlayedTracksRequest.Builder builder = GetUsersRecentlyPlayedTracksRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    return builder;
  }

  public GetUsersCurrentlyPlayingTrackRequest.Builder getCurrentlyPlayingTrack() {
    GetUsersCurrentlyPlayingTrackRequest.Builder builder = GetUsersCurrentlyPlayingTrackRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    return builder;
  }

  /**
   * Used to get Featured Playlists.
   *
   * @return A builder that can be used to build requests to get featured playlists.
   */
  public GetListOfFeaturedPlaylistsRequest.Builder getFeaturedPlaylists() {
    GetListOfFeaturedPlaylistsRequest.Builder builder = GetListOfFeaturedPlaylistsRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    return builder;
  }

  public GetArtistsTopTracksRequest.Builder getTopTracksForArtist(String id, CountryCode country) {
    GetArtistsTopTracksRequest.Builder builder = GetArtistsTopTracksRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    builder.id(id);
    builder.country(country);
    return builder;
  }

  public GetUsersProfileRequest.Builder getUser(String userId) {
    GetUsersProfileRequest.Builder builder = GetUsersProfileRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    userId = UriUtil.escapeUsername(userId);
    builder.username(userId);
    return builder;
  }

  public GetListOfUsersPlaylistsRequest.Builder getPlaylistsForUser(String userId) {
    GetListOfUsersPlaylistsRequest.Builder builder = GetListOfUsersPlaylistsRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    userId = UriUtil.escapeUsername(userId);
    builder.username(userId);
    return builder;
  }

  /**
   * Returns a builder that can be used to build requests for authorization code
   * grants.
   * Requires client ID, client secret, and redirect URI to be set.
   *
   * @param code An authorization code.
   * @return A builder that builds authorization code grant requests.
   */
  public AuthorizationCodeGrantRequest.Builder authorizationCodeGrant(String code) {
    AuthorizationCodeGrantRequest.Builder builder = AuthorizationCodeGrantRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    builder.grantType("authorization_code");
    builder.basicAuthorizationHeader(clientId, clientSecret);
    builder.code(code);
    builder.redirectUri(redirectURI);
    return builder;
  }

  /**
   * Returns a builder that can be used to build requests to refresh an access token
   * that has been retrieved using the authorization code grant flow.
   *
   * @return A builder that builds refresh access token requests.
   */
  public RefreshAccessTokenRequest.Builder refreshAccessToken() {
    RefreshAccessTokenRequest.Builder builder = RefreshAccessTokenRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    builder.grantType("refresh_token");
    builder.refreshToken(refreshToken);
    builder.basicAuthorizationHeader(clientId, clientSecret);
    return builder;
  }

  /**
   * Returns a builder that can be used to build requests for client credential grants.
   * Requires client ID and client secret to be set.
   *
   * @return A builder that builds client credential grant requests.
   */
  public ClientCredentialsGrantRequest.Builder clientCredentialsGrant() {
    ClientCredentialsGrantRequest.Builder builder = ClientCredentialsGrantRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    builder.grantType("client_credentials");
    builder.basicAuthorizationHeader(clientId, clientSecret);
    return builder;
  }

  /**
   * Get a playlist.
   *
   * @param userId     The playlist's owner's username.
   * @param playlistId The playlist's ID.
   * @return A builder object that can be used to build a request to retrieve a playlist.
   */
  public GetPlaylistRequest.Builder getPlaylist(String userId, String playlistId) {
    GetPlaylistRequest.Builder builder = GetPlaylistRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    userId = UriUtil.escapeUsername(userId);
    builder.setPath("/v1/users/" + userId + "/playlists/" + playlistId);
    return builder;
  }

  /**
   * Get information about the user that has given authorization to the application.
   *
   * @return A builder object that can be used to build a request to retrieve information
   * about the current user.
   */
  public GetCurrentUsersProfileRequest.Builder getMe() {
    final GetCurrentUsersProfileRequest.Builder builder = GetCurrentUsersProfileRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    return builder;
  }

  /**
   * Create a playlist.
   *
   * @param userId The playlist's owner.
   * @param name   The name of the playlist.
   * @return A builder object that can be used to build a request to create a playlist.
   */
  public CreatePlaylistRequest.Builder createPlaylist(String userId, String name) {
    final CreatePlaylistRequest.Builder builder = CreatePlaylistRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    builder.name(name);
    userId = UriUtil.escapeUsername(userId);
    builder.setPath("/v1/users/" + userId + "/playlists");
    return builder;
  }

  /**
   * Get artists related/similar to an artist.
   *
   * @param id The artist's id.
   * @return A builder object that can be used to build a request to retrieve similar artists.
   */
  public GetArtistsRelatedArtistsRequest.Builder getArtistRelatedArtists(String id) {
    final GetArtistsRelatedArtistsRequest.Builder builder = GetArtistsRelatedArtistsRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    builder.id(id);
    return builder;
  }

  /**
   * Get a playlist's tracks.
   *
   * @param userId     The playlist's owner's username.
   * @param playlistId The playlist's id.
   * @return A builder object that can be used to build a request to retrieve playlist tracks.
   */
  public GetPlaylistsTracksRequest.Builder getPlaylistTracks(String userId, String playlistId) {
    final GetPlaylistsTracksRequest.Builder builder = GetPlaylistsTracksRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    userId = UriUtil.escapeUsername(userId);
    builder.setPath("/v1/users/" + userId + "/playlists/" + playlistId + "/tracks");
    return builder;
  }

  /**
   * Get a user's starred tracks.
   *
   * @param userId The starred playlist's owner's username.
   * @return A builder object that can be used to build a request to retrieve a user's starred
   * tracks.
   */
  public GetPlaylistsTracksRequest.Builder getStarred(String userId) {
    final GetPlaylistsTracksRequest.Builder builder = GetPlaylistsTracksRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    userId = UriUtil.escapeUsername(userId);
    builder.setPath("/v1/users/" + userId + "/starred/tracks");
    return builder;
  }

  /**
   * Add tracks to a playlist.
   *
   * @param userId     The owner's username.
   * @param playlistId The playlist's ID.
   * @param trackUris  URIs of the tracks to add.
   * @return A builder object that can e used to build a request to add tracks to a playlist.
   */
  public AddTracksToPlaylistRequest.Builder addTracksToPlaylist(String userId, String playlistId, String[] trackUris) {
    final AddTracksToPlaylistRequest.Builder builder = AddTracksToPlaylistRequest.builder();

    userId = UriUtil.escapeUsername(userId);

    setDefaults(builder);
    builder.setQueryParameter("uris", Joiner.on(",").join(trackUris));
    builder.setPath("/v1/users/" + userId + "/playlists/" + playlistId + "/tracks");

    return builder;
  }

  /**
   * Replace tracks in a playlist.
   *
   * @param userId     The owner's username.
   * @param playlistId The playlist's ID.
   * @param trackUris  URIs of the tracks to add.
   * @return A builder object that can e used to build a request to add tracks to a playlist.
   */
  public ReplacePlaylistsTracksRequest.Builder replacePlaylistsTracks(String userId, String playlistId, String[] trackUris) {
    final ReplacePlaylistsTracksRequest.Builder builder = ReplacePlaylistsTracksRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    builder.setQueryParameter("uris", Joiner.on(",").join(trackUris));
    builder.setPath("/v1/users/" + userId + "/playlists/" + playlistId + "/tracks");
    return builder;
  }

  /**
   * delete tracks from a playlist
   *
   * @param userId     The owner's username.
   * @param playlistId The playlist's ID.
   * @param trackUris  URIs of the tracks to remove.
   * @return A builder object that can be used to build a request to remove tracks from a playlist.
   */
  public RemoveTracksFromPlaylistRequest.Builder removeTrackFromPlaylist(String userId, String playlistId, String[] trackUris) {
    final RemoveTracksFromPlaylistRequest.Builder builder = RemoveTracksFromPlaylistRequest.builder();

    userId = UriUtil.escapeUsername(userId);

    setDefaults(builder);
    builder.setQueryParameter("uris", Joiner.on(",").join(trackUris));
    builder.setPath("/v1/users/" + userId + "/playlists/" + playlistId + "/tracks");

    return builder;
  }

  /**
   * Update a playlist's properties.
   *
   * @param userId     The owner's username.
   * @param playlistId The playlist's ID.
   * @return A builder object that can be used to build a request to change a playlist's details.
   */
  public ChangePlaylistsDetailsRequest.Builder changePlaylistDetails(String userId, String playlistId) {
    final ChangePlaylistsDetailsRequest.Builder builder = ChangePlaylistsDetailsRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    userId = UriUtil.escapeUsername(userId);
    builder.setPath("/v1/users/" + userId + "/playlists/" + playlistId);
    return builder;
  }

  public RemoveTracksFromPlaylistRequest.Builder removeTrackFromPlaylist(String userId, String playlistId, PlaylistTrackPosition[] playlistTrackPositions) {
    final RemoveTracksFromPlaylistRequest.Builder builder = RemoveTracksFromPlaylistRequest.builder();

    builder.setDefaults(httpManager, scheme, host, port);

    JsonArray playlistTrackPositionJsonArray = new JsonArray();

    for (PlaylistTrackPosition playlistTrackPosition : playlistTrackPositions) {
      JsonObject playlistTrackPositionJsonObject = new JsonObject();

      playlistTrackPositionJsonObject.addProperty("uri", playlistTrackPosition.getUri());

      if (playlistTrackPosition.getPositions() != null) {
        JsonArray positionArray = new JsonArray();

        for (int position : playlistTrackPosition.getPositions()) {
          positionArray.add(position);
        }

        playlistTrackPositionJsonObject.add("positions", positionArray);
      }

      playlistTrackPositionJsonArray.add(playlistTrackPositionJsonObject);
    }

    JsonObject tracks = new JsonObject();
    tracks.add("tracks", playlistTrackPositionJsonArray);

    builder.setFormParameter("tracks", tracks.toString());
    builder.setPath("/v1/users/" + userId + "/playlists/" + playlistId + "/tracks");
    return builder;
  }

  public ReorderPlaylistsTracksRequest.Builder reorderTracksInPlaylist(String userId, String playlistId, int rangeStart, int insertBefore) {
    final ReorderPlaylistsTracksRequest.Builder builder = ReorderPlaylistsTracksRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    builder.rangeStart(rangeStart);
    builder.insertBefore(insertBefore);
    builder.setPath("/v1/users/" + userId + "/playlists/" + playlistId + "/tracks");
    return builder;
  }

  /**
   * Remove the current user as a follower of a playlist.
   *
   * @param owner_id    The owner's username.
   * @param playlist_id The playlist's ID.
   * @return A builder object that can be used to build a request
   * to remove the current user as a follower of a playlist.
   */
  public UnfollowPlaylistRequest.Builder unfollowPlaylist(String owner_id, String playlist_id) {
    final UnfollowPlaylistRequest.Builder builder = UnfollowPlaylistRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    builder.owner_id(owner_id).playlist_id(playlist_id);
    return builder;
  }

  /**
   * Get a users Your Music tracks.
   *
   * @return A builder object that can be used to build a request to get the user's Your Music library.
   */
  public GetUsersSavedTracksRequest.Builder getMySavedTracks() {
    final GetUsersSavedTracksRequest.Builder builder = GetUsersSavedTracksRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    builder.setPath("/v1/me/tracks");
    return builder;
  }

  /**
   * Check if a track is saved in the user's Your Music library.
   *
   * @param trackIds The tracks ids to check for in the user's Your Music library.
   * @return A builder object that can be used to check if a user has saved a track.
   */
  public CheckUsersSavedTracksRequest.Builder containsMySavedTracks(String... trackIds) {
    final CheckUsersSavedTracksRequest.Builder builder = CheckUsersSavedTracksRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    builder.tracks(trackIds);
    builder.setPath("/v1/me/tracks/contains");
    return builder;
  }

  /**
   * Remove a track if saved to the user's Your Music library.
   *
   * @param trackIds The track ids to remove from the user's Your Music library.
   * @return A builder object that can be used to remove tracks from the user's library.
   */
  public RemoveUsersSavedTracksRequest.Builder removeFromMySavedTracks(String... trackIds) {
    final RemoveUsersSavedTracksRequest.Builder builder = RemoveUsersSavedTracksRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    builder.ids(trackIds);
    builder.setPath("/v1/me/tracks");
    return builder;
  }

  /**
   * Save tracks in the user's Your Music library.
   *
   * @param trackIds The track ids to add to the user's library.
   * @return A builder object that can be used to add tracks to the user's library.
   */
  public SaveTracksForUserRequest.Builder addToMySavedTracks(String... trackIds) {
    final SaveTracksForUserRequest.Builder builder = SaveTracksForUserRequest.builder();
    builder.setDefaults(httpManager, scheme, host, port);
    builder.ids(trackIds);
    builder.setPath("/v1/me/tracks");
    return builder;
  }

  /**
   * Retrieve a URL where the user can give the application permissions.
   *
   * @param scopes     The scopes corresponding to the permissions the application needs
   * @param state      state A parameter that you can use to maintain a value between the request
   *                   and the callback to redirect_uri.It is useful to prevent CSRF exploits.
   * @param showDialog - (optional) whether or not to force the user to login
   * @return The URL where the user can give application permissions.
   */
  public URI createAuthorizeUri(String[] scopes, String state, boolean showDialog) {
    final AuthorizationUriRequest.Builder builder = AuthorizationUriRequest.builder();

    setDefaults(builder);

    builder.clientId(clientId);
    builder.responseType("code");
    builder.redirectURI(redirectURI);

    if (scopes != null) {
      builder.scopes(scopes);
    }

    if (state != null) {
      builder.state(state);
    }

    builder.showDialog(showDialog);

    return builder.build().getUri();
  }

  /**
   * Retrieve a URL where the user can give the application permissions.
   *
   * @param scopes The scopes corresponding to the permissions the application needs
   * @param state  state A parameter that you can use to maintain a value between the request
   *               and the callback to redirect_uri.It is useful to prevent CSRF exploits.
   * @return The URL where the user can give application permissions.
   */
  public URI createAuthorizeUri(String[] scopes, String state) {
    final AuthorizationUriRequest.Builder builder = AuthorizationUriRequest.builder();

    setDefaults(builder);

    builder.clientId(clientId);
    builder.responseType("code");
    builder.redirectURI(redirectURI);

    if (scopes != null) {
      builder.scopes(scopes);
    }

    if (state != null) {
      builder.state(state);
    }

    return builder.build().getUri();
  }

  /**
   * Retrieve a URL where the user can give the application permissions.
   * This method returns a builder instead, so that any optional parameters can be added.
   *
   * @param scopes The scopes corresponding to the permissions the application needs.
   * @return The URL where the user can give application permissions.
   */
  public URI createAuthorizeUri(String... scopes) {
    final AuthorizationUriRequest.Builder builder = AuthorizationUriRequest.builder();

    setDefaults(builder);

    builder.clientId(clientId);
    builder.responseType("code");
    builder.redirectURI(redirectURI);

    if (scopes != null) {
      builder.scopes(scopes);
    }

    return builder.build().getUri();
  }

  public GetRecommendationsRequest.Builder getRecommendations(String... ids) {
    GetRecommendationsRequest.Builder builder = GetRecommendationsRequest.builder();
    builder.seed_genres(ids);
    builder.setDefaults(httpManager, scheme, host, port);
    return builder;
  }

  public GetAvailableGenreSeedsRequest.Builder getAvailableGenreSeeds() {
    GetAvailableGenreSeedsRequest.Builder builder = new GetAvailableGenreSeedsRequest.Builder();
    builder.setDefaults(httpManager, scheme, host, port);
    return builder;
  }

  public GetListOfCategoriesRequest.Builder getCategories() {
    GetListOfCategoriesRequest.Builder builder = new GetListOfCategoriesRequest.Builder();
    builder.setDefaults(httpManager, scheme, host, port);
    return builder;
  }

  public GetCategoryRequest.Builder getCategory(String categoryId) {
    GetCategoryRequest.Builder builder = new GetCategoryRequest.Builder().category_id(categoryId);
    builder.setDefaults(httpManager, scheme, host, port);
    return builder;
  }

  public GetCategorysPlaylistsRequest.Builder getPlaylistsForCategory(String categoryId) {
    GetCategorysPlaylistsRequest.Builder builder = GetCategorysPlaylistsRequest.builder();
    builder.category_id(categoryId);
    return builder;
    builder.setDefaults(httpManager, scheme, host, port);
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }


  public static class Builder {

    private String host = DEFAULT_HOST;
    private int port = DEFAULT_PORT;
    private HttpManager httpManager = null;
    private String scheme = DEFAULT_SCHEME;
    private String accessToken;
    private String redirectURI;
    private String clientId;
    private String clientSecret;
    private String refreshToken;

    public Builder scheme(String scheme) {
      this.scheme = scheme;
      return this;
    }

    public Builder host(String host) {
      this.host = host;
      return this;
    }

    public Builder port(int port) {
      this.port = port;
      return this;
    }

    public Builder httpManager(HttpManager httpManager) {
      this.httpManager = httpManager;
      return this;
    }

    public Builder accessToken(String accessToken) {
      this.accessToken = accessToken;
      return this;
    }

    public Builder refreshToken(String refreshToken) {
      this.refreshToken = refreshToken;
      return this;
    }

    public Builder clientId(String clientId) {
      this.clientId = clientId;
      return this;
    }

    public Builder clientSecret(String clientSecret) {
      this.clientSecret = clientSecret;
      return this;
    }

    public Builder redirectURI(String redirectURI) {
      this.redirectURI = redirectURI;
      return this;
    }

    public Api build() {
      assert (host != null);
      assert (port > 0);
      assert (scheme != null);

      return new Api(this);
    }

  }

}

